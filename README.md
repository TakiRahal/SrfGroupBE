# SrfGroup BE

## Deploying Spring Boot Applications to Heroku

Just push on master branch: Connected with github





## Connect to database

heroku pg:psql postgresql-cubed-05974 --app srf-group-be



## Reset DataBase

https://data.heroku.com/datastores/99847b29-d576-4442-bd3c-b1d7223dce79#administration



## Deploy jar app heroku

run : mvn clean install -Pprod -DskipTests

run : heroku deploy:jar target/srfgroup-0.0.1-SNAPSHOT.jar --app srf-group-be

run: heroku open --app srf-group-be

heroku buildpacks:clear --app srf-group-be

log Heroku: heroku logs --app srf-group-be -t

## Custom command

change cmd: heroku config:set MAVEN_CUSTOM_GOALS="clean install -Pprod -DskipTests" --app srf-group-be


## ELK + Spring Boot

1) Run Spring boot (add logging.file.name)
    
2) Run Elasticsearch 
    + Version: elasticsearch-7.1.0
    + Under folder bin command: elasticsearch.bat
    + All the indexes that have ever been created inside elasticsearch: http://localhost:9200/_cat/indices
    + ElasticSearch actually received the data: curl 'http://localhost:9200/_search?pretty'
    
3) Run Kibana
    + Version: kibana-7.1.0
    + Under folder bin command: kibana.bat
    + Show data in Kibana:
        - Go to Management → Kibana Index Patterns → Create index Pattern
        - Enter “logstash-*” as the index pattern
        - Time filter : @timestamp
        - Go to the Discover tab in Kibana
        - Select logstash-* (Dropdown)
    
4) Run Logstash: 
    + Version: logstash-7.6.1
    + Add file config: logstash-7.6.1\bin\logstash.conf
    + Under folder bin command:  ./logstash -f logstash.conf
    
    
## SonarQube

mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=sonar


## Docker all parts Back: Spring boot + ELK
Run: mvn clean install -Pprod -DskipTests
run in the root project: 
    - Start: docker-compose -f docker/api.yml up
    - Down: docker-compose -f docker/api.yml down -v --remove-orphans





# Prespective

Search global on website (with elastic and index)