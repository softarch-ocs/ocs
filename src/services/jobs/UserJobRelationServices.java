package services.jobs;

import data.dao.HibernateUtil;
import data.entities.UsersJobs;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;


public class UserJobRelationServices {


    public Integer createUserJobRelation( int userID, int jobID, Date start, Date end ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer userJobID = null;
        try{
            tx = session.beginTransaction();

            UsersJobs userJob = new UsersJobs();

            /*userJob.setUserId( userID );
            userJob.setJobId( jobID );*/
            userJob.setStartTime( start );
            userJob.setEndTime( end );

            userJobID = ( Integer ) session.save( userJob );

            tx.commit();


        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();

        }

        session.close();
        return userJobID;
    }


}
