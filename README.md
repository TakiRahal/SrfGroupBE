# SrfGroup BE

## Deploying Spring Boot Applications to Heroku

Just push on master branch: Connected with github

log Heroku: heroku logs --app srf-group-be -t



## Connect to database

heroku pg:psql postgresql-cubed-05974 --app srf-group-be



## Reset DataBase

https://data.heroku.com/datastores/99847b29-d576-4442-bd3c-b1d7223dce79#administration



## Deploy jar app

heroku deploy:jar target/my-app.jar --app srf-group-be