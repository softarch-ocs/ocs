package services.jobs;

import data.dao.HibernateUtil;
import data.entities.JobFeature;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.SessionFactory;

public class FeatureServices {
    
    private SessionFactory sessionFactory;
    
    public FeatureServices( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }
    
    public FeatureServices() {
        this( HibernateUtil.getSessionFactory() );
    }

    public void createFeature( JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        
        try{
            tx = session.beginTransaction();           
            session.save( feature );
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();

        }        
    }

    public List<JobFeature> readAllFeatures(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobFeature> features = null;
        try{
            tx = session.beginTransaction();
            features = session.createCriteria( JobFeature.class ).list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return features;
    }

    public void updateFeature( JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            session.update( feature );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
    }

    public void deleteFeature( JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete( feature );
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
    }

}
