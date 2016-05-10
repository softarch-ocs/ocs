package services;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.User;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import services.exceptions.OcsPersistenceException;
import services.security.LDAPService;

public class UserService {

    private SessionFactory sessionFactory;
    private LDAPService ldapService;

    public UserService(SessionFactory sessionFactory, LDAPService ldapService) {
        this.sessionFactory = sessionFactory;
        this.ldapService = ldapService;
    }

    public UserService() {
        this(HibernateUtil.getSessionFactory(), new LDAPService());
    }

    public void registerNewUser(User user) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            session.save(user);
            ldapService.register(user);
            ctx.commit();
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public boolean login(String email, String password) {
        if (email == null) {
            throw new IllegalArgumentException("email");
        }

        if (password == null) {
            throw new IllegalArgumentException("password");
        }

        User user = getUserByEmail(email);

        if (user == null) {
            return false;
        }

        if (!ldapService.login(email, password)) {
            return false;
        }

        login(user);

        return true;
    }

    public void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user");
        }

        Map<String, Object> sessionMap
                = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        sessionMap.put("USER_ID", user.getId());
    }

    public String logout() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "/index.xhtml";
    }

    public User getLoggedInUser() {
        Map<String, Object> sessionMap
                = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        Integer id = (Integer) sessionMap.get("USER_ID");

        if (id == null) {
            return null;
        }

        return getUserById(id);
    }

    public User getFullUserById(int id) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            User result = (User) session.get(User.class, id);
            Hibernate.initialize(result.getJobFeatures());
            ctx.commit();

            return result;
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            User result = (User) session.get(User.class, id);
            ctx.commit();

            return result;
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            List<User> results = (List<User>) session.createCriteria(User.class)
                    .addOrder(Order.asc("lastName"))
                    .addOrder(Order.asc("firstName"))
                    .addOrder(Order.asc("email"))
                    .list();

            ctx.commit();

            return results;
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            List<User> results = (List<User>) session.createCriteria(User.class)
                    .add(Restrictions.eq("email", email))
                    .list();

            ctx.commit();

            return results.isEmpty() ? null : results.get(0);
        } catch (HibernateException ex) {
            throw new OcsPersistenceException(ex);
        }
    }

    public void updateUser(User newUser) {

        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {

            session.update(newUser);
            ctx.commit();

        } catch (HibernateException e) {
            throw new OcsPersistenceException(e);
        }
    }
}
