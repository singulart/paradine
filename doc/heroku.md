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