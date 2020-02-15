package com.trzewik.jdbc.db

import com.trzewik.jdbc.domain.Account
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.util.logging.Slf4j

@Slf4j
class DbHelper {
    private Sql sql
    private String defaultSchema

    DbHelper() {
        sql = Sql.newInstance(
            System.getProperty('db.url'),
            System.getProperty('db.username'),
            System.getProperty('db.password')
        )
        defaultSchema = System.getProperty('db.schema')
    }

    void dbCleanup(){
        deleteAccounts()
    }

    List<GroovyRowResult> getAllAccounts() {
        String query = "SELECT * FROM $accountTable"
        log.info(query)
        sql.rows(query)
    }

    void deleteAccounts() {
        String query = "DELETE FROM $accountTable"
        log.info(query)
        sql.execute(query)
    }

    List<List<Object>> save(Account account) {
        String query = "INSERT INTO $accountTable (username, email) VALUES (?, ?)"
        log.info(query)
        sql.executeInsert(query.toString(), [account.username, account.email])
    }

    private String getAccountTable() {
        return table('account')
    }

    private String table(String name) {
        return "$defaultSchema.$name".toString()
    }
}
