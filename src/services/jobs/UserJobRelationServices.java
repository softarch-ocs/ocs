package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.UsersJobs;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import services.exceptions.OcsPersistenceException;

public class UserJobRelationServices {

    private SessionFactory sessionFactory;

    public UserJobRelationServices(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserJobRelationServices() {
        this(HibernateUtil.getSessionFactory());
    }

    public void createUserJobRelation(UsersJobs userJob) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            session.save(userJob);
            ctx.commit();
        } catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }
    
    public void updateUserJobRelation(UsersJobs usersJobs) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            session.update(usersJobs);
            ctx.commit();
        } catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }

    public void deleteUserJobRelation(UsersJobs usersJobs) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try (TransactionContext ctx = new TransactionContext(session)) {
            session.delete(usersJobs);
            ctx.commit();
        } catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }

    public List<UsersJobs> getUsersJobsByUserId(int userId) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            List<UsersJobs> result = (List<UsersJobs>) session.createCriteria(UsersJobs.class)
                    .add(Restrictions.eq("user.id", userId))
                    .setFetchMode("job", FetchMode.JOIN)
                    .addOrder(Order.desc("endTime"))
                    .addOrder(Order.desc("startTime"))
                    .list();
            ctx.commit();

            return result;
        } catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }

    public UsersJobs getUsersJobsById(int id) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            UsersJobs result = (UsersJobs) session.get(UsersJobs.class, id);
            ctx.commit();

            return result;
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }
}
