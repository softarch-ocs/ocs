package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.JobRequest;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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
