package services.jobs;

import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class UserServices {

    private SessionFactory factory;

    public Integer createUser( String email, String password, String phoneNumber, String address,
                               String firstName, String lastName, String personalID, int role ){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;
        try{
            tx = session.beginTransaction();

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setAddress(address);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPersonalId(personalID);
            user.setRole( role );

            userID = (Integer) session.save( user );

            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }
        session.close();

        return userID;
    }

    public List<User> readAllUsers(){
        Session session = factory.openSession();
        Transaction tx = null;
        List<User> users = null;
        try{
            tx = session.beginTransaction();
            users = session.createQuery("FROM User").list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        session.close();

        return users;
    }

    //TODO query
    public List<Job> readUserJobs( int userID ){
        Session session = factory.openSession();
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

    //TODO query
    public List<JobFeature> readUserFeatures( int userID ){
        Session session = factory.openSession();
        Transaction tx = null;
        List<JobFeature> features = null;
        try{
            tx = session.beginTransaction();
            features = session.createQuery("FROM JobFeature").list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        session.close();

        return features;
    }

    public boolean updateUser( int userID, String email, String password, String phoneNumber, String address,
                               String firstName, String lastName, String personalID, int role ){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user = ( User ) session.get( User.class, userID );
            user.setEmail( email );
            user.setPassword( password );
            user.setPhoneNumber( phoneNumber );
            user.setAddress( address );
            user.setFirstName( firstName );
            user.setLastName( lastName );
            user.setPersonalId( personalID );
            user.setRole( role );

            session.update( user );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            session.close();
            return false;
        }

        session.close();

        return true;
    }

    public boolean deleteUser( int userID ){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            User user =
                    ( User )session.get( User.class, userID );
            session.delete( user );
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
