package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.UsersJobs;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import services.exceptions.OcsPersistenceException;


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
        
        try(TransactionContext ctx = new TransactionContext(session)){

            session.save( userJob );
            ctx.commit();

        }catch (HibernateException e) {
            throw new OcsPersistenceException(e);

        }    
    }

}
