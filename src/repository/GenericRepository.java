package repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import utils.HibernateSessionBuilder;

public abstract class GenericRepository<T> {

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void merge(T entity) {
        runInTransaction((session) -> session.merge(entity));
    }

    public void delete(T entity) {
        runInTransaction((session) -> session.delete(entity));
    }

    protected T findOne(Integer id, Class<T> clazz) {
        try (Session session = openSession()) {
            return session.find(clazz, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void runInTransaction(Consumer<Session> operation) {
        Transaction transaction = null;
        try (Session session = openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the object
            operation.accept(session);
            // commit transaction
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    protected List<T> findAll(String query, Class<T> clazz) {
        try (Session session = openSession()) {
            return session.createQuery(query, clazz).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Session openSession() {
        return HibernateSessionBuilder.getInstance().openSession();
    }
}