package services;

import data.dao.HibernateUtil;
import data.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserService {
    public void registerNewUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            session.save(user);
            tx.commit();
        } catch (HibernateException ex) {
            tx.rollback();
        }
    }

    public User getUserByEmailAndPassword(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return (User)session.createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .list().get(0);
    }
}
