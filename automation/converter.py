"""
Converts Google Places API Json response from drobnikj/crawler-google-places
into a number of SQL inserts into main tables: restaurant, popular_time and working_hours.

Usage: python3 converter.py /Users/o.buistov/projects/experi-mental/crawler-google-places/apify_storage/datasets/default/\*.json 2
2 is the id of city Kharkiv
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
    if ':01' in hour:
        inc = int(hour.split(':')[0])
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':01')[1]
    if ':20' in hour:
        inc = int(hour.split(':')[0])
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':20')[1]
    if ':45' in hour:
        inc = int(hour.split(':')[0]) + 1
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':45')[1]
    if ':55' in hour:
        inc = int(hour.split(':')[0]) + 1
        if inc == 12 and 'PM' in hour:
            return 24
        else:
            hour = str(inc) + hour.split(':55')[1]
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


def execute(path, city):
    alter_sequence_before = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 1;'
    alter_sequence_after = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 50;'
    print(alter_sequence_before)
    files = glob.glob(path)

    # restaurants ingestion
    insert_header = 'INSERT INTO RESTAURANT (ID, UUID, CITY_ID, CAPACITY, GEOLAT, GEOLNG, NAME, PHOTO_URL, GOOGLE_PLACES_ID, CREATED_AT, UPDATED_AT, ADDRESS_EN, ADDRESS_UA) VALUES \n'
    print(insert_header)
    restaurant_rows = []
    for f in files:
        with open(f, 'r') as popul:
            rest_dict = json.loads(popul.read())
            row_sql_values = "((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, %d, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s) " % \
                             (quote(str(uuid.uuid4())), int(city), 99,
                              rest_dict['location']['lat'],
                              rest_dict['location']['lng'],
                              quote(rest_dict['title']),
                              quote('http://www.todo.com'),
                              quote(rest_dict['placeId']),
                              'CURRENT_TIMESTAMP',
                              'CURRENT_TIMESTAMP',
                              quote(rest_dict['address'].split('   ')[0].strip()) if rest_dict['address'] else quote(''),
                              quote(rest_dict['address'].split('   ')[-1].strip()) if rest_dict['address'] else quote(''))
            restaurant_rows.append(row_sql_values)
    print(',\n'.join(restaurant_rows) + ';')

    print('create index RESTAURANT_GOOGLE_PLACES_ID_INDEX on RESTAURANT (GOOGLE_PLACES_ID);')

    # popular times ingestion
    column_names = ", ".join("OCC_%02d" % h for h in range(1, 25))
    insert_header = "INSERT INTO POPULAR_TIME (ID, RESTAURANT_ID, DAY_OF_WEEK, %s) VALUES \n" % column_names
    print(insert_header)
    pop_times_rows = []
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
                    map_from_sorted_list = {int(x['hour']): x for x in sorted_list}
                    pop_times = []
                    for t in range(1, 25):
                        if t in map_from_sorted_list.keys():
                            pop_times.append(str(map_from_sorted_list[t]['occupancyPercent']))
                        else:
                            pop_times.append('0')
                    occ_values = ", ".join(pop_times)
                    row_sql_values = "((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, '%s', %s)" % (
                    resto_id_clause, dow, occ_values)
                    pop_times_rows.append(row_sql_values)
                else:
                    row_sql_values = "((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, '%s', %s)" % (resto_id_clause, dow,
                                                   ", ".join('0' for _ in range(1, 25)))
                    pop_times_rows.append(row_sql_values)
    print(',\n'.join(pop_times_rows) + ';')

    # working hours ingestion
    insert_header = "INSERT INTO WORKING_HOURS (ID, RESTAURANT_ID, DAY_OF_WEEK, OPENING_HOUR, CLOSING_HOUR, CLOSED) VALUES "
    print(insert_header)
    wh_rows = []
    for f in files:
        with open(f, 'r') as popul:
            rest_dict = json.loads(popul.read())
            resto_id_clause = "(SELECT ID FROM RESTAURANT WHERE GOOGLE_PLACES_ID = '%s')" % rest_dict['placeId']
            if 'openingHours' not in rest_dict.keys():
                continue
            opening_hours = rest_dict['openingHours']
            for opening in opening_hours:
                day_no_comma = opening['day'].replace(',', '')  # cleanup weird comma character
                if not day_no_comma in WEEK:
                    print("-- '%s' is not a week day. working hours are possibly hidden" % day_no_comma)
                    continue
                day = WEEK[day_no_comma]
                hours = opening['hours']\
                    .replace(', Hours might differ', '').replace('(Independence Day of Ukraine), ', '')
                if 'Closed' in hours:
                    wh_rows.append("((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, %s, %s, %s, %s)" % (
                                                   resto_id_clause, quote(day), 'NULL', 'NULL', 'True'))
                elif '24 hours' in hours:
                    wh_rows.append("((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, %s, %s, %s, %s)" % (
                        resto_id_clause, quote(day), '0', '24', 'False'))
                else:
                    if ',' in hours:
                        print('-- handling comma in working hours, next statement needs inspection')
                        hours = hours.split(',')[-1]
                    # print(hours)
                    open_hour = parse_hour(hours.split('to')[0].strip())
                    close_hour = parse_hour(hours.split('to')[1].strip())
                    wh_rows.append("((SELECT NEXTVAL('SEQUENCE_GENERATOR')), %s, %s, %s, %s, %s)" % (
                        resto_id_clause, quote(day), str(open_hour), str(close_hour), 'False'))
    print(',\n'.join(wh_rows) + ';')
    print('drop index RESTAURANT_GOOGLE_PLACES_ID_INDEX;')
    print(alter_sequence_after)


if __name__ == '__main__':
    execute(sys.argv[1], sys.argv[2])
