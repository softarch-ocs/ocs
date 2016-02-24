package controllers;
import data.dao.HibernateUtil;
import data.entities.Job;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



@ManagedBean
@ViewScoped
public class HandleJobController {
    private Job entity;
    private Long id;

    public Long getId() {
        return id;
    }

    public void initEdit(Long id){
        System.out.println("Init edit");
        this.id = id;
    }

    public boolean isEditing(){
        SessionFactory sessionFactory;

        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        sessionFactory.openSession();

        System.out.println("Is editing");
        return id != null;
    }

    public Job getEntity() {
        return entity;
    }

    public void save(){
        //TODO: Set logic of saving or updating
    }
}
