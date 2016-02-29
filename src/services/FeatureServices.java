package services;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import services.exceptions.OcsPersistenceException;

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
        
        try(TransactionContext ctx = new TransactionContext(session)){
            session.save( feature );
            ctx.commit();
        }catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }        
    }
    
    public JobFeature getFeatureById( int id ){
        Session session = sessionFactory.getCurrentSession();
        JobFeature feature = null;
        try(TransactionContext ctx = new TransactionContext(session)){
            feature = ( JobFeature ) session.get( JobFeature.class, id );
            ctx.commit();
        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
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
        
        List<JobFeature> features = null;
        try(TransactionContext ctx = new TransactionContext(session)){
            features = session.createCriteria( JobFeature.class ).list();
            ctx.commit();
        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return features;
    }

    public void updateFeature( JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        try(TransactionContext ctx = new TransactionContext(session)){
            
            session.update( feature );
            ctx.commit();

        }catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }
    
    public void deleteFeature( Job job, JobFeature feature ){

        Session session = sessionFactory.getCurrentSession();
        
        try(TransactionContext ctx = new TransactionContext(session)){
            job.getJobFeatures().remove( feature );
            session.update( job );
            ctx.commit();
        }catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }

    private Job readJobWithFeatures(int jobID) {
        Session session = sessionFactory.getCurrentSession();
        Job job = null;
        try(TransactionContext ctx = new TransactionContext(session)){
            job = ( Job ) session.createCriteria(Job.class).add( Restrictions.eq("id", jobID) ).setFetchMode("jobFeatures", FetchMode.JOIN).uniqueResult();
            ctx.commit();

        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return job;
    }
    
    private User readUserWithFeatures( int userID ) {
        Session session = sessionFactory.getCurrentSession();
        User user = null;
        try(TransactionContext ctx = new TransactionContext(session)){
            user = ( User ) session.createCriteria( User.class ).add( Restrictions.eq("id", userID) ).setFetchMode("jobFeatures", FetchMode.JOIN).uniqueResult();
            ctx.commit();

        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return user;
    }
    
}
