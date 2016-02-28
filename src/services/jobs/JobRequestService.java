package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.JobRequest;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import services.exceptions.OcsPersistenceException;

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
            jobRequests = session.createCriteria(JobRequest.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequests;
    }

    public void changeStatusJobRequest(JobRequest jobRequest) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(jobRequest);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

    }

}
