package controllers;

import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;
import java.util.ArrayList;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.FeatureServices;
import services.UserService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class ShowFeaturesController {
    
    private final UserService userService;
    private final JobServices jobService;
    private final FeatureServices featureService;
    
    private List<JobFeature> userFeatures;
    private List<JobFeature> jobFeatures;
    private List<JobFeature> features;

    public List<JobFeature> getUserFeatures() {
        return userFeatures;
    }
    
    public List<JobFeature> getJobFeatures() {
        return jobFeatures;
    }
    
    public List<JobFeature> getFeatures() {
        return features;
    }

    public void setUserFeatures( List<JobFeature> userFeatures ) {
        this.userFeatures = userFeatures;
    }
    
    public void setJobFeatures( List<JobFeature> jobFeatures ) {
        this.jobFeatures = jobFeatures;
    }
    
    public void setFeatures( List<JobFeature> features ) {
        this.features = features;
    }

    public ShowFeaturesController( ){
        userService = new UserService();
        featureService = new FeatureServices();
        jobService = new JobServices();
        
        userFeatures = new ArrayList<>();
        jobFeatures = new ArrayList<>();
        features = featureService.readAllFeatures();
        
        User user = userService.getLoggedInUser();
        
        if( user != null ){
            userFeatures = featureService.readFeatures( user );
        }
 
    }
    
    public void initJobFeatures( Long id ){
         if( id != null ){
            Job job = jobService.readJob( Integer.parseInt( id + "" ) );
            if( job != null ){
                jobFeatures = featureService.readFeatures( job );
            }
        }
    }
}
