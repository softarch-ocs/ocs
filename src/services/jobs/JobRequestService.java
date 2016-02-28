package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.Job;
import data.entities.JobRequest;
import data.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    
    

}
