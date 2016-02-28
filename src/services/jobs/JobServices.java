package services.jobs;

import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import org.hibernate.SessionFactory;

public class JobServices {

    private SessionFactory sessionFactory;
    
    public JobServices( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }
    
    public JobServices() {
        this( HibernateUtil.getSessionFactory() );
    }
    
    
    public void createJob( Job job ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        
        try{
            tx = session.beginTransaction();
            session.save( job );
            tx.commit();

        }catch ( HibernateException e ) {
            if (tx!=null) tx.rollback();
        }

    }

    public List<Job> readAllJobs(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<Job> jobs = null;
        try{
            tx = session.beginTransaction();
            jobs = session.createCriteria( Job.class ).list();
            tx.commit();
        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return jobs;
    }

    
    //TODO replace query with criterias
    public List<User> readUsersInAJob( int jobID ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<User> jobs = null;
        
        try{
            tx = session.beginTransaction();
            
            jobs = session.createQuery("SELECT User FROM UsersJobs UJ, User U WHERE UJ.jobId = U.jobID").list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }


        return jobs;
    }

    public List<JobFeature> readJobFeatures( int jobID ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobFeature> features = null;
        try{
            tx = session.beginTransaction();
            Job job = (Job) session.get( Job.class, jobID );
            if( job != null ){
                features = job.getJobFeatures();
            }
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return features;
    }

    public Job readJob( int jobID ){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        Job job = null;
        try{
            tx = session.beginTransaction();
            job = ( Job ) session.get( Job.class, jobID );
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return job;
    }
    
    public void updateJob( Job newJob ){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            session.update( newJob );
            tx.commit();

        }catch ( HibernateException e ) {
            if (tx!=null) tx.rollback();
        }
    }
    

    public void deleteJob( Job job ){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete( job );
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
        
    }

}
