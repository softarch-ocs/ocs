package services;

import data.dao.HibernateUtil;
import data.entities.User;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserService {
    private SessionFactory sessionFactory;
    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public UserService() {
        this(HibernateUtil.getSessionFactory());
    }
    
    public void registerNewUser(User user) {
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        try {
            session.save(user);
            tx.commit();
        } catch (HibernateException ex) {
            tx.rollback();
            throw ex;
        }
    }
    
    public boolean login(String email, String password) {
        if (email == null) {
            throw new IllegalArgumentException("email");
        }
        
        if (password == null) {
            throw new IllegalArgumentException("password");
        }
        
        User user = getUserByEmailAndPassword(email, password);
        
        if (user == null) {
            return false;
        }
        
        login(user);
        
        return true;
    }
    
    public void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user");
        }
        
        Map<String, Object> sessionMap = 
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        sessionMap.put("USER_ID", user.getId());
    }
    
    public User getLoggedInUser() {
        Map<String, Object> sessionMap = 
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        Integer id = (Integer)sessionMap.get("USER_ID");
        
        if (id == null) {
            return null;
        }
        
        return getUserById(id);
    }
    
    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return (User)session.get(User.class, id);
    }
    
    private User getUserByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        return (User)session.createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .list().get(0);
    }
}
