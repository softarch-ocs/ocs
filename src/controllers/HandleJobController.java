package controllers;
import data.dao.HibernateUtil;
import data.entities.Job;
import jdk.nashorn.internal.runtime.ListAdapter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Array;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class HandleJobController {
    private Job entity;
    private Long id;
    private String lok;

    public HandleJobController(){
        super();
        entity = new Job();
    }

    public Long getId() {
        return id;
    }

    public void initEdit(Long id){
        System.out.println("Init edit");
        this.id = id;
    }

    public boolean isEditing(){
        return id != null;
    }

    public Job getEntity() {
        return entity;
    }

    public void setEntity(Job entity){
        this.entity = entity;
    }

    public void save(){
        //TODO: Set logic of saving or updating
        System.out.println("here");
        Session session;
        session = HibernateUtil.getSessionFactory().openSession();
        List existingJobs = new ArrayList();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Job.class)
                    .add(Restrictions.eq("id", entity.getId()));
            existingJobs = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            session.beginTransaction();

            if(existingJobs.isEmpty()){
                session.save(entity);
            }else{
                Job updatedJob = (Job) existingJobs.get(0);
                updatedJob.setDescription(entity.getDescription());
                updatedJob.setJobFeatures(entity.getJobFeatures());
                updatedJob.setName(entity.getName());
                updatedJob.setSalary(entity.getSalary());
                session.update(updatedJob);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }



        System.out.println("Is editing");
    }
}
