package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.Job;
import data.entities.JobArea;
import data.entities.JobFeature;
import data.entities.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.FetchMode;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import services.exceptions.OcsPersistenceException;

public class JobServices {

    private SessionFactory sessionFactory;
    
    public JobServices( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }
    
    public JobServices() {
        this( HibernateUtil.getSessionFactory() );
    }
    
    public List<JobArea> readAllJobsArea(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobArea> areas = null;
        try{
            tx = session.beginTransaction();
            areas = session.createCriteria( JobArea.class ).list();
            tx.commit();
        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return areas;
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
    
    
    public List<Job> readAllJobsWithArea(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<Job> jobs = null;
        try{
            tx = session.beginTransaction();
            jobs = session.createCriteria( Job.class ).setFetchMode("jobArea", FetchMode.JOIN).list();
            tx.commit();
        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return jobs;
    }
    
    public Job readJobWithFeatures(int jobID){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        Job job = null;
        try{
            tx = session.beginTransaction();
            job = (Job)session.createCriteria( Job.class ).add(Restrictions.eq("id", jobID)).setFetchMode("jobFeatures", FetchMode.JOIN).list().get(0);
            tx.commit();
        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return job;
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

    public JobArea getJobAreaById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            JobArea result = (JobArea)session.get(JobArea.class, id);
            ctx.commit();
            
            return result;
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public Job readJobWithJobArea(int jobID) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        Job job = null;
        try{
            tx = session.beginTransaction();
            job = ( Job ) session.createCriteria(Job.class).add( Restrictions.eq("id", jobID) ).setFetchMode("jobArea", FetchMode.JOIN).uniqueResult();
            
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return job;
    }
    
    

}
