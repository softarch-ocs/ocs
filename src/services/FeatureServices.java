package services;

import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;

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
    
    public JobFeature getFeatureById( int id ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        JobFeature feature = null;
        try{
            tx = session.beginTransaction();
            feature = ( JobFeature ) session.get( JobFeature.class, id );
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return feature;
    }
    
    public List<JobFeature> readFeatures( User user ){
        return user.getJobFeatures();
    }
    
    public List<JobFeature> readFeatures( Job job ){
        return job.getJobFeatures();
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

    public void addFeature( User user, JobFeature feature ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            user.getJobFeatures().add( feature );
            session.update( user );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
    }
    
    public void addFeature( Job job, JobFeature feature ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            job.getJobFeatures().add( feature );
            session.update( job );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
    }
    
    public void deleteFeature( User user, JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            user.getJobFeatures().remove( feature );
            session.update( user );
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
    }
    
    public void deleteFeature( Job job, JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            job.getJobFeatures().remove( feature );
            session.update( job );
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
    }

}
