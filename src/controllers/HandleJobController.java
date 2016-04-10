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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import presentation.beans.HandleJobBean;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.exceptions.OcsValidationException;


@ManagedBean
@RequestScoped
public class HandleJobController extends BaseController {
    
    
    private JobServices jobServices;
    
    @ManagedProperty("#{handleJobBean}")
    private HandleJobBean bean;
    
    
    
    public HandleJobController(JobServices jobServices, UserService userService) {
        super(userService);
        if(jobServices == null){
            throw new IllegalArgumentException("jobServices");
        }
        
        this.jobServices = jobServices;
    }

    public JobServices getJobServices() {
        return jobServices;
    }

    public void setJobServices(JobServices jobServices) {
        this.jobServices = jobServices;
    }

    public HandleJobBean getBean() {
        return bean;
    }

    public void setBean(HandleJobBean bean) {
        this.bean = bean;
    }
    
    

    public HandleJobController(){
        this(new JobServices(), new UserService());
                      
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        if( !bean.isEditing() ){
            bean.setEntity( new Job() );
            bean.getEntity().setName("");
            bean.getEntity().setSalary(0);
            bean.getEntity().setDescription("");
        }
        
        bean.setAreas(jobServices.readAllJobsArea());
    }

    public void initEdit(Long id){
        System.out.println("Init edit " + id);
        
        bean.setId(id);
        bean.setEntity(new Job());
        bean.getEntity().setName("");
        bean.getEntity().setSalary(0);
        bean.getEntity().setDescription("");
        if(id!=null)
            bean.setEntity( jobServices.readJobWithJobArea( id.intValue() ) );
        
    }

    public void save(){
      //  bean.getEntity().setId( bean.getId().intValue() );
        System.out.println("ola k ase ----> " + bean.getEntity().getJobArea());
        if( bean.getEntity().getJobArea() == null ){
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
                    
            
            return;
        }
        try{
               
            if( bean.isEditing() ){
                jobServices.updateJob( bean.getEntity() );
            }else{
                jobServices.createJob( bean.getEntity() );
            }
        }catch( OcsValidationException ex ){
            
            for( OcsValidationException.ValidationItem item : ex.getValidationItems() ){
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage( item.getMessage() ) );    
            }
        }catch(OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(ex.toString() + " " + bean.getEntity().getName() ) );
        }
    }
}
