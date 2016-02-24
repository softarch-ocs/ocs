package services.jobs;


import data.entities.JobFeature;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class FeatureServices {

    private SessionFactory factory;

    public Integer createFeature( String name, String description ){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer featureID = null;
        try{
            tx = session.beginTransaction();

            JobFeature feature = new JobFeature();

            feature.setName(name);
            feature.setDescription(description);

            featureID = ( Integer ) session.save( feature );

            tx.commit();


        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();

        }

        session.close();
        return featureID;
    }

    public List<JobFeature> readAllFeatures(){
        Session session = factory.openSession();
        Transaction tx = null;
        List<JobFeature> features = null;
        try{
            tx = session.beginTransaction();
            features = session.createQuery("FROM JobFeature ").list();
            tx.commit();

        }catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
        }

        session.close();

        return features;
    }

    public boolean updateFeature( int featureID, String name, String description ){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            JobFeature feature = ( JobFeature ) session.get( JobFeature.class, featureID );
            feature.setName(name);
            feature.setDescription(description);

            session.update( feature );
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            session.close();
            return false;
        }

        session.close();

        return true;
    }

    public boolean deleteFeature( int featureID ){

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            JobFeature feature =
                    ( JobFeature )session.get( JobFeature.class, featureID );
            session.delete( feature );
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
