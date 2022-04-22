# SrfGroup BE

## Deploying Spring Boot Applications to Heroku

Just push on master branch: Connected with github

log Heroku: heroku logs --app srf-group-be -t



## Connect to database

heroku pg:psql postgresql-cubed-05974 --app srf-group-be



## Reset DataBase

https://data.heroku.com/datastores/99847b29-d576-4442-bd3c-b1d7223dce79#administration



## Deploy jar app

run : mvn clean install -Pprod -DskipTests

heroku deploy:jar target/srfgroup-0.0.1-SNAPSHOT.jar --app srf-group-be

heroku open --app srf-group-be

heroku buildpacks:clear --app srf-group-be


## ELK + Spring Boot

1) Run Spring boot (add logging.file.name)
    
2) Run Elasticsearch 
    + Version: elasticsearch-6.2.3
    + Under folder bin command: elasticsearch.bat
    
3) Run Kibana
    + Version: kibana-6.0.0
    + Under folder bin command: kibana.bat
    
4) Run Logstash: 
    + Version: logstash-7.6.1
    + Add file config: logstash-7.6.1\bin\logstash.conf
    + Under folder bin command:  ./logstash -f logstash.conf