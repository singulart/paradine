import jaydebeapi
conn = jaydebeapi.connect(
    'org.h2.Driver',
    'jdbc:h2:file:../h2/paradine;AUTO_SERVER=True',
    ['thehipsta', ''],
    '/Users/o.buistov/.m2/repository/com/h2database/h2/1.4.200/h2-1.4.200.jar'
)
curs = conn.cursor()
curs.execute("select GOOGLE_PLACES_ID from PUBLIC.RESTAURANT")
rs = curs.fetchall()
print(',\n'.join(['"place_id:' + t[0] + '"' for t in rs]))
curs.close()
conn.close()
