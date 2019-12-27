package com.trzewik.jdbc.db

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

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

    List<GroovyRowResult> getAllAccounts() {
        sql.rows("SELECT * FROM ${defaultSchema}.account".toString())
    }

    GroovyRowResult getAccountByUserId(long id) {
        sql.rows("SELECT * FROM ${defaultSchema}.account WHERE user_id = ${id}".toString())?.first()
    }

    List<GroovyRowResult> getAccountsByEmail(String email) {
        sql.rows("SELECT * FROM ${defaultSchema}.account WHERE email = '${email}'".toString())
    }

    List<GroovyRowResult> getAccountsByUsername(String username) {
        sql.rows("SELECT * FROM ${defaultSchema}.account WHERE username = '${username}'".toString())
    }

    List<GroovyRowResult> getAccounts(Account account) {
        sql.rows("SELECT * FROM ${defaultSchema}.account WHERE username = '${account.username}' AND email = '${account.email}'".toString())
    }

    List<List<Object>> insert(Account account) {
        sql.executeInsert("INSERT INTO ${defaultSchema}.account (username, email) VALUES ('${account.username}', '${account.email}')".toString())
    }

    void deleteAccountsByIds(List<Long> userIds) {
        userIds.each {
            sql.execute("DELETE FROM ${defaultSchema}.account WHERE user_id = ${it}".toString())
        }
    }

    void deleteAccounts(List<Account> accounts) {
        List<Long> userIds = []
        accounts.each {userIds.addAll(getAccounts(it).user_id)}
        userIds.each {
            sql.execute("DELETE FROM ${defaultSchema}.account WHERE user_id = ${it}".toString())
        }
    }
}
