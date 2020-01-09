## Requirements
* [Docker](https://www.docker.com/get-started)
* JAVA

##What is  it
Simple app which using jdbc to interact with database.
When start `com.trzewik.jdbc.App` it will be possible to use console based interface to select/insert/delete/update
`com.trzewik.jdbc.db.Account` records from database table `account`.

## How to start

* `docker-compose up` - setup container with database for local development
* `gradlew flywayMigrate -i` - run db migrations

