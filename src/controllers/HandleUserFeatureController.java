package controllers;

import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;
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
public class HandleUserFeatureController {

    private final FeatureServices featureServices;
    private final UserService userServices;

    private Integer userId;

    public HandleUserFeatureController() {
        super();
        featureServices = new FeatureServices();
        userServices = new UserService();
    }

    @PostConstruct
    public void initialize() {
        String ret = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId");
        if (ret == null) {
            throw new IllegalArgumentException("userId not initilizated: HandleUserFeatureController: initialize()");
        }
        this.userId = Integer.parseInt( ret );
    }

    public Integer getUserId() {
        return userId;
    }
    
    public boolean isFeatureInUser( JobFeature feature ){
        
        if(feature == null){
            throw new IllegalArgumentException("feature");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId");
        }
        
        User user = userServices.getUserById(Integer.parseInt(userId + ""));
        
        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        List<JobFeature> features = featureServices.readFeatures(user);
        if (features == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return features.contains(feature);
        
    }

    public String joinFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId");
        }

        User user = userServices.getUserById(Integer.parseInt(userId + ""));

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        user.getJobFeatures().add(feature);

        userServices.updateUser(user);

        return "/features/showUserFeatures.xhtml?faces-redirect=true&id=" + userId;
    }
    
    public String deleteFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (jobId == null) {
            throw new IllegalArgumentException("jobId");
        }

        Job job = jobServices.readJob(Integer.parseInt(jobId + ""));

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        job.getJobFeatures().remove(feature);

        jobServices.updateJob(job);

        return "/features/showJobFeatures.xhtml?faces-redirect=true&id=" + jobId;
    }

}
