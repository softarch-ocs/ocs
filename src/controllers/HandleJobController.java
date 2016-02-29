package controllers;

import data.entities.Job;
import data.entities.JobArea;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import services.jobs.JobServices;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.exceptions.OcsServiceException;


@ManagedBean
@ViewScoped
public class HandleJobController {
    private Job entity;
    private Long id;
    private List<JobArea> areas;
    private JobServices jobServices;
    
    public HandleJobController(JobServices jobServices){
        if(jobServices == null){
            throw new IllegalArgumentException("jobServices");
        }
        
        this.jobServices = jobServices;
    }

    public HandleJobController(){
        this( new JobServices() );
        entity = new Job();
        entity.setName("");
        entity.setSalary(0);
        entity.setDescription("");
        
        areas = jobServices.readAllJobsArea();               
    }

    public Long getId() {
        return id;
    }
    
    public List<JobArea> getAreas(){
        return areas;
    }

    public void initEdit(Long id){
        System.out.println("Init edit");
        this.id = id;
        entity = new Job();
        entity.setName("");
        entity.setSalary(0);
        entity.setDescription("");
        if(id!=null)
            this.entity = jobServices.readJobWithJobArea(Integer.parseInt(""+id));
        
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
        System.out.println("ola k ase ----> " + entity.getJobArea());
        if( entity.getJobArea() == null ){
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
            
            return;
        }
        try{
            if( isEditing() ){
                jobServices.updateJob( entity );
            }else{
                jobServices.createJob( entity );
            }
        } catch(OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
        }
    }
}
