package data.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionContext implements AutoCloseable {
    private Transaction transaction;
    
    public TransactionContext(Session session) {
        transaction = session.beginTransaction();
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    public void commit() {
        transaction.commit();
    }
    
    public void rollback() {
        transaction.rollback();
    }
    
    @Override
    public void close() {
        if (!transaction.wasCommitted()) {
            transaction.rollback();
        }
    }
}
