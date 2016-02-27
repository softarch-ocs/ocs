package services.jobs;

import data.dao.HibernateUtil;
import data.entities.UsersJobs;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import org.hibernate.SessionFactory;


public class UserJobRelationServices {
    
    private SessionFactory sessionFactory;

    public UserJobRelationServices( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }
    
    public UserJobRelationServices( ){
        this( HibernateUtil.getSessionFactory() );
    }

    public void createUserJobRelation( UsersJobs userJob ) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        
        try{
            tx = session.beginTransaction();
            session.save( userJob );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();

        }    
    }

}
