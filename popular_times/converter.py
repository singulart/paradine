"""
Converts Google Places API Json response from drobnikj/crawler-google-places
into a number of SQL inserts into popular_time table.
"""

import json
import glob

insert_header = """INSERT INTO POPULAR_TIME \
(ID, RESTAURANT_ID, DAY_OF_WEEK, \
OCC_06, OCC_07, OCC_08, OCC_09, OCC_10, OCC_11, \
OCC_12, OCC_13, OCC_14, OCC_15, OCC_16, OCC_17, \
OCC_18, OCC_19, OCC_20, OCC_21, OCC_22, OCC_23) VALUES \n"""
alter_sequence_before = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 1;'
alter_sequence_after = 'ALTER SEQUENCE SEQUENCE_GENERATOR INCREMENT BY 50;'


rows = []
files = glob.glob('*.json')
for f in files:
    resto_id_clause = \
        "(SELECT ID FROM RESTAURANT " \
        "WHERE GOOGLE_PLACES_ID = '%s')" % f.split('.')[0]
    with open(f, 'r') as popul:
        pop_dict = json.loads(popul.read())
        pop_hist = pop_dict[0]['popularTimesHistogram']
        for dow in pop_hist:
            occ_values = ", "\
                .join("%d" % d['occupancyPercent'] for d in pop_hist[dow])
            row_sql_values = """((SELECT NEXTVAL('SEQUENCE_GENERATOR')), \
%s, '%s', %s)""" % (resto_id_clause, dow, occ_values)
            rows.append(row_sql_values)

print("".join([alter_sequence_before, "\n", insert_header,
               ",\n ".join(rows), ";\n", alter_sequence_after]))
