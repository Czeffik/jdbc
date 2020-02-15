package com.trzewik.jdbc.db

import org.flywaydb.core.Flyway
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Specification

abstract class DbSpec extends Specification {
    static final String DEFAULT_SCHEMA = 'test'
    private static PostgreSQLContainer container
    private static Flyway flyway
    protected static DbHelper dbHelper

    def setupSpec() {
        startContainer()
        setSystemProperties()
        setupFlyway()
        migrateDb()
        dbHelper = new DbHelper()
    }

    def cleanupSpec() {
        flyway.clean()
        container.stop()
    }

    private static void startContainer() {
        if (container == null) {
            container = new PostgreSQLContainer()
            container.start()
        }
    }

    private static void migrateDb() {
        flyway.migrate()
    }

    private static void setupFlyway() {
        if (flyway == null) {
            flyway = Flyway.configure().dataSource(
                container.jdbcUrl,
                container.username,
                container.password)
                .schemas(DEFAULT_SCHEMA)
                .defaultSchema(DEFAULT_SCHEMA)
                .load()
        }
    }

    private static void setSystemProperties() {
        System.setProperty('db.username', container.username)
        System.setProperty('db.password', container.password)
        System.setProperty('db.url', container.jdbcUrl)
        System.setProperty('db.schema', DEFAULT_SCHEMA)
    }
}
