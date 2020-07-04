"""
Converts Google Places API Json response from drobnikj/crawler-google-places
into a number of SQL inserts into popular_time and working_hours table.

Usage: python3 converter.py /Users/o.buistov/projects/experi-mental/crawler-google-places/apify_storage/datasets/default/\*.json
"""

import json
import glob
import sys
import uuid

WEEK = {
    'Sunday': 'Su',
    'Monday': 'Mo',
    'Tuesday': 'Tu',
    'Wednesday': 'We',
    'Thursday': 'Th',
    'Friday': 'Fr',
    'Saturday': 'Sa'
}


def parse_hour(hour):
    if hour == '12AM':
        return 24
    if ':30' in hour:
        inc = int(hour.split(':')[0]) + 1
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':30')[1]
    if ':45' in hour:
        inc = int(hour.split(':')[0]) + 1
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':45')[1]
    if ':59' in hour:
        inc = int(hour.split(':')[0]) + 1
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':59')[1]
    hour = hour.replace('AM', '')
    if 'PM' in hour:
        if hour != '12PM':
            hour = int(hour.replace('PM', '')) + 12
        else:
            return 12
    return hour


def quote(s):
    return "'" + s.replace("'", "''") + "'"


def execute(path):
    alter_sequence_before = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 1;'
    alter_sequence_after = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 50;'
    print(alter_sequence_before)
    files = glob.glob(path)

    # restaurants ingestion
    for f in files:
        with open(f, 'r') as popul:
            rest_dict = json.loads(popul.read())
            insert_header = 'INSERT INTO RESTAURANT (ID, UUID, CAPACITY, GEOLAT, GEOLNG, NAME, PHOTO_URL, GOOGLE_PLACES_ID, CREATED_AT, UPDATED_AT, ADDRESS_EN, ADDRESS_UA) VALUES '
            row_sql_values = "((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s);" % \
                             (quote(str(uuid.uuid4())), 99,
                              rest_dict['location']['lat'],
                              rest_dict['location']['lng'],
                              quote(rest_dict['title']),
                              quote('http://www.todo.com'),
                              quote(rest_dict['placeId']),
                              'LOCALTIMESTAMP()',
                              'LOCALTIMESTAMP()',
                              quote(rest_dict['address'].split('   ')[0].strip()) if rest_dict['address'] else quote(''),
                              quote(rest_dict['address'].split('   ')[-1].strip()) if rest_dict['address'] else quote(''))
            print(insert_header + row_sql_values)

    print('create index RESTAURANT_GOOGLE_PLACES_ID_INDEX on RESTAURANT (GOOGLE_PLACES_ID);')

    # popular times ingestion
    for f in files:
        with open(f, 'r') as popul:
            rest_dict = json.loads(popul.read())
            if 'popularTimesHistogram' not in rest_dict.keys():
                continue
            pop_hist = rest_dict['popularTimesHistogram']
            resto_id_clause = "(SELECT ID FROM RESTAURANT WHERE GOOGLE_PLACES_ID = '%s')" % rest_dict['placeId']
            for dow in pop_hist:
                if len(pop_hist[dow]) > 0:
                    # remove duplicated hours
                    deduped_list = [
                        i for n, i in enumerate(pop_hist[dow])
                        if i.get('hour') not in [y.get('hour') for y in pop_hist[dow][n + 1:]]
                    ]
                    sorted_list = sorted(deduped_list, key=lambda i: i['hour'])
                    column_names = ", ".join("OCC_%02d" % h['hour'] for h in sorted_list)
                    insert_header = "INSERT INTO POPULAR_TIME (ID, RESTAURANT_ID, DAY_OF_WEEK, %s) VALUES " % column_names
                    occ_values = ", ".join("%d" % d['occupancyPercent'] for d in sorted_list)
                    row_sql_values = "(SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, '%s', %s" % (
                    resto_id_clause, dow, occ_values)
                    print("".join([insert_header, '(', row_sql_values, ');']))
                else:
                    insert_header = "INSERT INTO POPULAR_TIME (ID, RESTAURANT_ID, DAY_OF_WEEK) VALUES "
                    row_sql_values = "(SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, '%s'" % (resto_id_clause, dow)
                    print("".join([insert_header, '(', row_sql_values, ');']))

    # working hours ingestion
    for f in files:
        with open(f, 'r') as popul:
            rest_dict = json.loads(popul.read())
            resto_id_clause = "(SELECT ID FROM RESTAURANT WHERE GOOGLE_PLACES_ID = '%s')" % rest_dict['placeId']
            if 'openingHours' not in rest_dict.keys():
                continue
            opening_hours = rest_dict['openingHours']
            for opening in opening_hours:
                day = WEEK[opening['day'].replace(',', '')]  # cleanup weird comma character
                hours = opening['hours']
                if 'Closed' in hours:
                    insert_header = "INSERT INTO WORKING_HOURS (ID, RESTAURANT_ID, DAY_OF_WEEK, CLOSED) VALUES "
                    print("".join([insert_header,
                                   '(', ', '.join(["(SELECT NEXTVAL('SEQUENCE_GENERATOR'))",
                                                   resto_id_clause, quote(day), 'True']), ');']))
                elif '24 hours' in hours:
                    insert_header = "INSERT INTO WORKING_HOURS (ID, RESTAURANT_ID, DAY_OF_WEEK, OPENING_HOUR, CLOSING_HOUR) VALUES "
                    print("".join([insert_header,
                                   '(', ', '.join(["(SELECT NEXTVAL('SEQUENCE_GENERATOR'))",
                                                   resto_id_clause, quote(day), '0', '24']), ');']))
                else:
                    if ',' in hours:
                        print('-- handling comma in working hours, next statement needs inspection')
                        hours = hours.split(',')[-1]
                    open_hour = parse_hour(hours.split('to')[0].strip())
                    close_hour = parse_hour(hours.split('to')[1].strip())
                    insert_header = "INSERT INTO WORKING_HOURS (ID, RESTAURANT_ID, DAY_OF_WEEK, OPENING_HOUR, CLOSING_HOUR) VALUES "
                    print("".join([insert_header,
                                   '(', ', '.join(["(SELECT NEXTVAL('SEQUENCE_GENERATOR'))",
                                                   resto_id_clause, quote(day), str(open_hour), str(close_hour)]), ');']))
    print('drop index RESTAURANT_GOOGLE_PLACES_ID_INDEX;')
    print(alter_sequence_after)


if __name__ == '__main__':
    execute(sys.argv[1])
