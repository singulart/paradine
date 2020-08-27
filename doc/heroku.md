заливать дамп так

```
pg_dump -Fc --no-acl --no-owner -h localhost -U paradine paradine -f dump/prdn.dmp
aws s3 sync ./dump s3://ua-com-paradine/dbdump
```

сходить в AWS консоль и сделать соотв папку публичной

и затем
```
heroku pg:backups:restore -a paradine https://ua-com-paradine.s3.eu-central-1.amazonaws.com/dbdump/prdn.dmp DATABASE_URL --confirm paradine
```

переменные среды
```
SERVER_SSL_ENABLED:                  false
SERVER_SSL_KEY_ALIAS:                paradine
SERVER_SSL_KEY_STORE_PASSWORD:       .....
SPRING_DATASOURCE_DRIVER_CLASS_NAME: org.postgresql.Driver
SPRING_DATASOURCE_PASSWORD:          ......
SPRING_DATASOURCE_URL:               jdbc:postgresql://ec2-54-246-87-132.eu-west-1.compute.amazonaws.com:5432/d5g95ibmssdbut
SPRING_DATASOURCE_USERNAME:          ....
SPRING_PROFILES_ACTIVE:              prod
```

TODO
разобраться с проблемой с портами при 
SERVER_SSL_ENABLED:                  true
