package controllers;

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

@ManagedBean
@ViewScoped
public class UserFeaturesController extends BaseController {
    
    private final FeatureServices featureService;

    private List<JobFeature> userFeatures;
    
    private Integer id;

    public List<JobFeature> getUserFeatures() {
        return userFeatures;
    }

    public void setUserFeatures(List<JobFeature> userFeatures) {
        this.userFeatures = userFeatures;
    }

    public UserFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();

        userFeatures = new ArrayList<>();

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

        initUserFeatures(id);
    }
    
    public void initUserFeatures(Integer id) {
        
        User user = userService.getUserById(id);
        if(user == null){
            throw new IllegalArgumentException("user");
        }
        
        userFeatures = featureService.readFeatures(user);

    }
    
    
    
    
    
    
    

    public Integer getUserId() {
        return id;
    }
    
    public boolean isFeatureInUser( JobFeature feature ){
        
        if(feature == null){
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }
        
        User user = userService.getUserById(id);
        
        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        userFeatures = featureService.readFeatures(user);
        if (userFeatures == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return userFeatures.contains(feature);
        
    }

    public void joinFeatureUser(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        User user = userService.getFullUserById(id);

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        if(isFeatureInUser(feature)){
            throw new IllegalArgumentException("Feauture already in user");
        }
        
        user.getJobFeatures().add(feature);
        
        userService.updateUser(user);
        initUserFeatures(id);
    }
    
    public String deleteFeatureUser(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        User user = userService.getFullUserById(id);

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        
        user.getJobFeatures().remove(feature);

        userService.updateUser(user);

        initUserFeatures(id);
        
        return "/features/showUserFeatures.xhtml?faces-redirect=true&id=" + id;
    }
    
}
