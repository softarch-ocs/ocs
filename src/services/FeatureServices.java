package services;

import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

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
        user = readUserWithFeatures( user.getId() );
        return user.getJobFeatures();
    }
    
    public List<JobFeature> readFeatures( Job job ){
        job = readJobWithFeatures( job.getId() );
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

    private Job readJobWithFeatures(int jobID) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        Job job = null;
        try{
            tx = session.beginTransaction();
            job = ( Job ) session.createCriteria(Job.class).add( Restrictions.eq("id", jobID) ).setFetchMode("jobFeatures", FetchMode.JOIN).uniqueResult();
            
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return job;
    }
    
    private User readUserWithFeatures( int userID ) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            user = ( User ) session.createCriteria( User.class ).add( Restrictions.eq("id", userID) ).setFetchMode("jobFeatures", FetchMode.JOIN).uniqueResult();
            
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return user;
    }
    
}
