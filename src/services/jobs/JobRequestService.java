package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.JobRequest;
import data.entities.JobRequest.Status;
import data.entities.User;
import data.entities.UsersJobs;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import services.FeatureServices;
import services.exceptions.OcsPersistenceException;
import services.exceptions.OcsValidationException;

public class JobRequestService {

    private SessionFactory sessionFactory;

    public JobRequestService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public JobRequestService() {
        this(HibernateUtil.getSessionFactory());
    }

    public void createJobRequest(JobRequest jobRequest) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            session.save(jobRequest);
            ctx.commit();
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public List readAllJobRequest() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobRequest> jobRequests = null;
        try {
            tx = session.beginTransaction();
            jobRequests = session.createCriteria(JobRequest.class).setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequests;
    }

    public JobRequest readJobRequest(int jobRequestId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        JobRequest jobRequest = null;
        try {
            tx = session.beginTransaction();
            jobRequest = (JobRequest) session.createCriteria(JobRequest.class)
                    .add(Restrictions.eq("id", jobRequestId))
                    .setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequest;
    }
    
    public JobRequest readJobRequest(User user, Job job, Status status) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        JobRequest jobRequest = null;
        try {
            tx = session.beginTransaction();
            jobRequest = (JobRequest) session.createCriteria(JobRequest.class)
                    .add(Restrictions.eq("user", user))
                    .add(Restrictions.eq("job", job))
                    .add(Restrictions.eq("status", status))
                    .setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequest;
    }

    public void changeStatusJobRequest(JobRequest jobRequest) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(jobRequest);

            if (jobRequest.getStatus() == JobRequest.Status.ACCEPTED) {
                UsersJobs userJobs = new UsersJobs();
                userJobs.setUser(jobRequest.getUser());
                userJobs.setJob(jobRequest.getJob());
                Calendar calendar = Calendar.getInstance();
                userJobs.setStartTime(new Date(calendar.getTime().getTime()));
                userJobs.setEndTime(null);
                session.save(userJobs);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

    }

    public boolean checkJobRequirements(User user, Job job) {

        FeatureServices featureServices = new FeatureServices(sessionFactory);

        List userFeatures = featureServices.readFeatures(user);
        List jobFeatures = featureServices.readFeatures(job);

        if (userFeatures.containsAll(jobFeatures)) {
            return true;
        } else {
            throw new OcsValidationException(new OcsValidationException.ValidationItem(
                    "Sorry, you don't fulfill the necessary requirements to postulate to this job"));
        }

    }

    public void checkAvailability(JobRequest jobRequest) {
                
        if (readJobRequest(jobRequest.getUser(), jobRequest.getJob(), jobRequest.getStatus()) != null) {
            throw new OcsValidationException(new OcsValidationException.ValidationItem(
                    "Sorry, you can't postulate again to this job"));
        }
        
        //TODO check job in user

    }

}
