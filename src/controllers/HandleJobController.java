package controllers;
import data.dao.HibernateUtil;
import data.entities.Job;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import services.jobs.JobServices;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class HandleJobController {
    private Job entity;
    private Long id;

    public HandleJobController(){
        super();
        entity = new Job();
        entity.setName("name placeholder");
        entity.setSalary(0x7fffff);
        entity.setDescription("description placeholder");
    }

    public Long getId() {
        return id;
    }

    public void initEdit(Long id){
        System.out.println("Init edit");
        this.id = id;
        entity.setName("name placeholder");
        entity.setSalary(0x7fffff);
        entity.setDescription("description placeholder");
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

        JobServices jobServices = new JobServices();
        if(isEditing()){
            jobServices.updateJob(this.id, entity);
        }else{
            jobServices.createJob(entity.getName(), entity.getDescription(), entity.getSalary());
        }
    }
}
