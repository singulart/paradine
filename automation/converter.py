"""
Converts Google Places API Json response from drobnikj/crawler-google-places
into a number of SQL inserts into popular_time table.

Usage: python3 converter.py /Users/o.buistov/projects/experi-mental/crawler-google-places/apify_storage/datasets/default/\*.json
"""

import json
import glob
import sys


def execute(path):
    alter_sequence_before = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 1;'
    alter_sequence_after = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 50;'
    print(alter_sequence_before)
    files = glob.glob(path)
    for f in files:
        with open(f, 'r') as popul:
            pop_dict = json.loads(popul.read())
            if 'popularTimesHistogram' not in pop_dict.keys():
                continue
            pop_hist = pop_dict['popularTimesHistogram']
            resto_id_clause = "(SELECT ID FROM RESTAURANT WHERE GOOGLE_PLACES_ID = '%s')" % pop_dict['placeId']
            for dow in pop_hist:
                if len(pop_hist[dow]) > 0:
                    deduped_list = [dict(t) for t in
                                    {tuple(d.items()) for d in pop_hist[dow]}]  # remove duplicated hours
                    sorted_list = sorted(deduped_list, key=lambda i: i['hour'])
                    hour = sorted_list[0]['hour']
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
    print(alter_sequence_after)


if __name__ == '__main__':
    execute(sys.argv[1])
