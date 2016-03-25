package controllers;

import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;
import java.util.ArrayList;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import services.FeatureServices;
import services.UserService;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class ShowFeaturesController extends BaseController {
    
    private final FeatureServices featureService;

    private List<JobFeature> features;

    public List<JobFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<JobFeature> features) {
        this.features = features;
    }

    public ShowFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();

        features = featureService.readAllFeatures();

    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }

}
