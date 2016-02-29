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
import org.hibernate.Hibernate;

import org.hibernate.SessionFactory;
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
        List<JobArea> areas = null;
        
        try (TransactionContext ctx = new TransactionContext(session)) {
            areas = session.createCriteria( JobArea.class ).list();
            ctx.commit();
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }

        return areas;
    }
    
    public void createJob( Job job ){
        Session session = sessionFactory.getCurrentSession();
        
        try( TransactionContext ctx = new TransactionContext( session ) ){
            session.save( job );
            ctx.commit();

        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

    }

    public List<Job> readAllJobs(){
        Session session = sessionFactory.getCurrentSession();
        List<Job> jobs = null;
        
        try(TransactionContext ctx = new TransactionContext(session)){
            jobs = session.createCriteria( Job.class ).list();
            ctx.commit();
        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return jobs;
    }
    
    
    public List<Job> readAllJobsWithArea(){
        Session session = sessionFactory.getCurrentSession();
        List<Job> jobs = null;
        try(TransactionContext ctx = new TransactionContext(session)){
            jobs = session.createCriteria( Job.class ).setFetchMode("jobArea", FetchMode.JOIN).list();
            ctx.commit();
        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return jobs;
    }
    
    public Job readJobWithFeatures(int jobID){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        Job job = null;
        try{
            tx = session.beginTransaction();
            job = (Job)session.createCriteria( Job.class ).add(Restrictions.eq("id", jobID)).setFetchMode("jobFeatures", FetchMode.JOIN).uniqueResult();
            tx.commit();
        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        return job;
    }

    
    //TODO replace query with criterias
    public List<User> readUsersInAJob( int jobID ){
        Session session = sessionFactory.getCurrentSession();
        List<User> jobs = null;
        
        try(TransactionContext ctx = new TransactionContext(session)){
            jobs = session.createQuery("SELECT User FROM UsersJobs UJ, User U WHERE UJ.jobId = U.jobID").list();
            ctx.commit();
        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }


        return jobs;
    }

    public List<JobFeature> readJobFeatures( int jobID ){
        Session session = sessionFactory.getCurrentSession();
        List<JobFeature> features = null;
        
        try( TransactionContext ctx = new TransactionContext(session)){
            Job job = (Job) session.get( Job.class, jobID );
            if( job == null ){
                throw new IllegalArgumentException("job");
            }
            features = job.getJobFeatures();
            ctx.commit();

        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return features;
    }

    public Job readJob( int jobID ){
        Session session = sessionFactory.getCurrentSession();
        Job job = null;
        try(TransactionContext ctx = new TransactionContext(session)){
            job = ( Job ) session.get( Job.class, jobID );
            
            if(job == null){
                throw new IllegalArgumentException("job");
            }
            
            Hibernate.initialize( job.getJobFeatures() );
            ctx.commit();
        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return job;
    }
    
    public void updateJob( Job newJob ){

        Session session = sessionFactory.getCurrentSession();

        try(TransactionContext ctx = new TransactionContext(session)){

            session.update( newJob );
            ctx.commit();

        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }
    }
    

    public void deleteJob( Job job ){

        Session session = HibernateUtil.getSessionFactory().openSession();
        try(TransactionContext ctx = new TransactionContext(session)){
            session.delete( job );
            ctx.commit();
        }catch (HibernateException e) {
            throw new OcsPersistenceException(e);
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
        Job job = null;
        try(TransactionContext ctx = new TransactionContext(session)){

            job = ( Job ) session.createCriteria(Job.class).add( Restrictions.eq("id", jobID) ).setFetchMode("jobArea", FetchMode.JOIN).uniqueResult();
            ctx.commit();

        }catch ( HibernateException e ) {
            throw new OcsPersistenceException(e);
        }

        return job;
    }
    
    

}
