package com.trzewik.jdbc.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

class Db<T> {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    Db(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    T get(long id, Class<T> tClass) {
        openSession();
        T t = session.get(tClass, id);
        closeSession();
        return t;
    }

    List<T> getAll(String query, Class<T> tClass) {
        openSession();
        List<T> list = session.createQuery(query, tClass).getResultList();
        closeSession();
        return list;
    }

    void save(T toSave) {
        openSessionWithTransaction();
        session.save(toSave);
        closeSessionWithTransaction();
    }

    void update(T updated) {
        openSessionWithTransaction();
        session.update(updated);
        closeSessionWithTransaction();
    }

    void delete(T toDelete) {
        openSessionWithTransaction();
        session.delete(toDelete);
        closeSessionWithTransaction();
    }

    private void openSession() {
        session = sessionFactory.openSession();
    }

    private void openSessionWithTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private void closeSession() {
        session.close();
    }

    private void closeSessionWithTransaction() {
        transaction.commit();
        session.close();
    }
}
