package controllers;

import data.entities.JobFeature;
import data.entities.User;
import java.util.ArrayList;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.FeatureServices;
import services.UserService;

@ManagedBean
@ViewScoped
public class ShowUserFeaturesController {
    
    private final UserService userService;
    private final FeatureServices featureService;
    
    private List<JobFeature> features;

    public List<JobFeature> getFeatures() {
        return features;
    }

    public void setFeatures( List<JobFeature> features ) {
        this.features = features;
    }

    public ShowUserFeaturesController(){
        userService = new UserService();
        featureService = new FeatureServices();
        
        features = new ArrayList<>();
        
        User user = userService.getLoggedInUser();
        
        if( user != null ){
            features = featureService.readFeatures( user );
        }
        
    }
    
}
