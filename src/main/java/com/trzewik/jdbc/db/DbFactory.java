package com.trzewik.jdbc.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DbFactory {

    public static Dao<Account> accountDao() {
        return new AccountDao(db());
    }

    static Db db() {
        SessionFactory factory = sessionFactory(properties());
        return new Db(factory);
    }

    private static DbProperties properties() {
        return new DbProperties();
    }

    private static SessionFactory sessionFactory(DbProperties properties) {
        Configuration configuration = createHibernateConfiguration(properties);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    private static Configuration createHibernateConfiguration(DbProperties properties) {
        return new Configuration()
            .setProperty("hibernate.connection.driver_class ", properties.getDriverClass())
            .setProperty("hibernate.connection.url", properties.getUrl())
            .setProperty("hibernate.connection.username", properties.getUsername())
            .setProperty("hibernate.connection.password", properties.getPassword())
            .setProperty("hibernate.default_schema", properties.getDefaultSchema())
            .addAnnotatedClass(Account.class);
    }
}
