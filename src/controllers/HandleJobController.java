package controllers;

import data.entities.Job;
import data.entities.JobArea;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import services.jobs.JobServices;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


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
        entity.setName("name placeholder");
        entity.setSalary(0);
        entity.setDescription("description placeholder");
        
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
        entity.setName("name placeholder");
        entity.setSalary(0);
        entity.setDescription("description placeholder");
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
        if( isEditing() ){
            jobServices.updateJob( entity );
        }else{
            jobServices.createJob( entity );
        }
    }
}
