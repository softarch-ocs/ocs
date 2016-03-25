package controllers;

import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;
import java.util.ArrayList;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.FeatureServices;
import services.UserService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class JobFeaturesController extends BaseController {
    
    private final FeatureServices featureService;
    private final JobServices jobService;

    private List<JobFeature> jobFeatures;
    
    private Integer id;

    public List<JobFeature> getJobFeatures() {
        return jobFeatures;
    }

    public void setJobFeatures(List<JobFeature> jobFeatures) {
        this.jobFeatures = jobFeatures;
    }

    public JobFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();
        jobFeatures = new ArrayList<>();
        jobService = new JobServices();
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        String ret = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(ret == null){
            throw new IllegalArgumentException("id ShowFeaturesController:initUserFeatures");
        }
        id = Integer.parseInt(ret);
        if (id == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:initUserFeatures");
        }

        initJobFeatures(id);
    }
    
    public void initJobFeatures(Integer id) {
        
        Job job = jobService.readJob(id);
        if(job == null){
            throw new IllegalArgumentException("user");
        }
        
        jobFeatures = featureService.readFeatures(job);

    }
    
    
    
    
    
    
    

    public Integer getJobId() {
        return id;
    }
    
    public boolean isFeatureInJob( JobFeature feature ){
        
        if(feature == null){
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }
        
        Job job = jobService.readJob(id);
        
        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        jobFeatures = featureService.readFeatures(job);
        if (jobFeatures == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return jobFeatures.contains(feature);
        
    }

    public void joinFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobService.readJob(id);

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        if(isFeatureInJob(feature)){
            throw new IllegalArgumentException("Feauture already in job");
        }
        
        job.getJobFeatures().add(feature);
        
        jobService.updateJob(job);
        initJobFeatures(id);
    }
    
    public String deleteFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobService.readJob(id);

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        
        job.getJobFeatures().remove(feature);

        jobService.updateJob(job);

        initJobFeatures(id);
        
        return "/features/showJobFeatures.xhtml?faces-redirect=true&id=" + id;
    }
    
}
