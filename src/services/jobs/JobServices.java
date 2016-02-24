package services.jobs;


import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class JobServices {


    public Integer createJob( String name, String description, int salary ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer jobID = null;
        try{
            tx = session.beginTransaction();

            Job job = new Job();

            job.setName(name);
            job.setDescription(description);
            job.setSalary( salary );

            jobID = ( Integer ) session.save( job );

            tx.commit();


        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();

        }

        session.close();
        return jobID;
    }


    public List<Job> readAllJobs(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Job> jobs = null;
        try{
            tx = session.beginTransaction();
            jobs = session.createQuery("FROM Job").list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        session.close();

        return jobs;
    }

    public List<User> readUsersInAJob( int jobID ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<User> jobs = null;
        try{
            tx = session.beginTransaction();
            jobs = session.createQuery("SELECT User FROM UsersJobs UJ INNER JOIN User WHERE UJ.jobId = :jobID").list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        session.close();

        return jobs;
    }

    public List<JobFeature> readJobFeatures( int jobID ){
        Session session = HibernateUtil.getSessionFactory().openSession();
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

        session.close();

        return features;
    }

    public boolean updateJob( int jobID, String name, String description, int salary ){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Job job = ( Job ) session.get( Job.class, jobID );
            job.setName(name);
            job.setDescription(description);
            job.setSalary(salary);

            session.update( job );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            session.close();
            return false;
        }

        session.close();

        return true;
    }
    
    public Job readJob( int jobID ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Job job = null;
        try{
            tx = session.beginTransaction();
            job = ( Job ) session.get( Job.class, jobID );
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        session.close();

        return job;
    }
    
    
    
    /*public boolean updateJob( long jobID, Job entity ){
        

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Job job = ( Job ) session.get( Job.class, jobID );
            job.setDescription(entity.getDescription());
            job.setName(entity.getName());
            job.setSalary(entity.getSalary());

            session.update( job );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            session.close();
            return false;
        }

        session.close();

        return true;
    } */

    public boolean deleteJob( int jobID ){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Job job =
                    ( Job )session.get( Job.class, jobID );
            session.delete( job );
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            session.close();
            return false;
        }

        session.close();
        return true;
    }

}
