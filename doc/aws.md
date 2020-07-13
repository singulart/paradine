##### Если не удаляются энвы - удалить вначале стеки CloudFormation https://aws.amazon.com/premiumsupport/knowledge-center/elastic-beanstalk-deletion-failure/

##### https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb3-create.html

```
mvn clean package -DskipTests=true -B -Pwar,prod
zip -r paradine.zip .ebextensions
cd target
zip -r ../paradine.zip paradine-1.0.0-SNAPSHOT.jar
cd ..
mv paradine.zip target
eb create --single --database.engine postgres 
```

Manual steps:

1) EC2 security groups: Enable inbound traffic on port 5000. How to automate this?
2) RDS security group (name contains AWSEBRDSDBSecurityGroup): Enable inbound traffic on port 5432. How to automate this?

Больше ничего не надо делать!!! Энва доступна по такому адресу

https://paradine-dev.eu-central-1.elasticbeanstalk.com:5000/


Базу eb cli создает с именем ebdb (Как это переопределить? Возможно тут написано https://stackoverflow.com/questions/25946723/aws-cli-create-rds-with-elasticbeanstalk-create-environment)

eb cli ставит размер базы 10Гиг (Как это переопределить?)




Полезные опции eb create
--instance-types t2.small 
--keyname paradine-ssh-key-pair


