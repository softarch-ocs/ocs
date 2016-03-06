package controllers;

import data.entities.Job;
import data.entities.JobArea;
import data.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import services.jobs.JobServices;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.exceptions.OcsValidationException;


@ManagedBean
@ViewScoped
public class HandleJobController extends BaseController {
    private Job entity;
    private Long id;
    private List<JobArea> areas;
    private JobServices jobServices;
    
    public HandleJobController(JobServices jobServices, UserService userService) {
        super(userService);
        if(jobServices == null){
            throw new IllegalArgumentException("jobServices");
        }
        
        this.jobServices = jobServices;
    }

    public HandleJobController(){
        this(new JobServices(), new UserService());
        entity = new Job();
        entity.setName("");
        entity.setSalary(0);
        entity.setDescription("");
        
        areas = jobServices.readAllJobsArea();               
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
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
            int kha = 1;
            if( isEditing() ){
                jobServices.updateJob( entity );
            }else{
                jobServices.createJob( entity );
            }
        }catch( OcsValidationException ex ){
            
            for( OcsValidationException.ValidationItem item : ex.getValidationItems() ){
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage( item.getMessage() ) );    
            }
        }catch(OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
        }
    }
}
