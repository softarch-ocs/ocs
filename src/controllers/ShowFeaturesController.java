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
    private final JobServices jobService;
    private final FeatureServices featureService;

    private List<JobFeature> userFeatures;
    private List<JobFeature> jobFeatures;
    private List<JobFeature> notUserFeatures;
    private List<JobFeature> notJobFeatures;
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
    
    public List<JobFeature> getNotUserFeatures() {
        return notUserFeatures;
    }
    
    public List<JobFeature> getNotJobFeatures() {
        return notJobFeatures;
    }

    public void setUserFeatures(List<JobFeature> userFeatures) {
        this.userFeatures = userFeatures;
    }

    public void setJobFeatures(List<JobFeature> jobFeatures) {
        this.jobFeatures = jobFeatures;
    }

    public void setFeatures(List<JobFeature> features) {
        this.features = features;
    }

    public ShowFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();
        jobService = new JobServices();

        userFeatures = new ArrayList<>();
        jobFeatures = new ArrayList<>();
        notUserFeatures = new ArrayList<>();
        notJobFeatures = new ArrayList<>();
        features = featureService.readAllFeatures();

    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }

    public void initJobFeatures(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:initJobFeatures");
        }

        Job job = jobService.readJob(id);
        if (job != null) {
            jobFeatures = featureService.readFeatures(job);
        }

    }
    
    public void initUserFeatures(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:initUserFeatures");
        }

        User user = userService.getUserById(id);
        if (user != null) {
            userFeatures = featureService.readFeatures(user);
        }

    }
    
    public void initNotJobFeatures(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:initJobFeatures");
        }

        Job job = jobService.readJob(id);
        if (job != null) {
            jobFeatures = featureService.readFeatures(job);
        }

    }
    
    public void initNotUserFeatures(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:initUserFeatures");
        }

        User user = userService.getUserById(id);
        if (user != null) {
            userFeatures = featureService.readFeatures(user);
        }

    }
}
