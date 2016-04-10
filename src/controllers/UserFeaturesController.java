package controllers;

import data.entities.JobFeature;
import data.entities.User;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.UserFeaturesBean;
import services.FeatureServices;
import services.UserService;

@ManagedBean
@RequestScoped
public class UserFeaturesController extends BaseController {
    
    private final FeatureServices featureService;

    @ManagedProperty("#{userFeaturesBean}")
    private UserFeaturesBean bean;
    
    public UserFeaturesBean getBean() {
        return bean;
    }
    
    public void setBean( UserFeaturesBean bean ){
        this.bean = bean;
    }
    
    public List<JobFeature> getUserFeatures() {
        return bean.getUserFeatures();
    }

    public void setUserFeatures(List<JobFeature> userFeatures) {
        bean.setUserFeatures(userFeatures);
    }

    public UserFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        String ret = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(bean.getId() == null && ret == null){
            throw new IllegalArgumentException("id ShowFeaturesController:initUserFeatures");
        }
        if(bean.getId() == null) {
            bean.setId(Integer.parseInt(ret));
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:initUserFeatures");
        }

        initUserFeatures(bean.getId());
    }
    
    public void initUserFeatures(Integer id) {
        
        User user = userService.getUserById(id);
        if(user == null){
            throw new IllegalArgumentException("user");
        }
        
        bean.setUserFeatures(featureService.readFeatures(user));
    }
    
    public Integer getUserId() {
        return bean.getId();
    }
    
    public boolean isFeatureInUser( JobFeature feature ){
        
        if(feature == null){
            throw new IllegalArgumentException("feature");
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id");
        }
        
        User user = userService.getUserById(bean.getId());
        
        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        bean.setUserFeatures(featureService.readFeatures(user));
        
        if (bean.getUserFeatures() == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return bean.getUserFeatures().contains(feature);
        
    }

    public void joinFeatureUser(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id");
        }

        User user = userService.getFullUserById(bean.getId());

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        if(isFeatureInUser(feature)){
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("The feature is already in the user"));
            
            return;
        }
        
        user.getJobFeatures().add(feature);
        
        userService.updateUser(user);
        initUserFeatures(bean.getId());
    }
    
    public String deleteFeatureUser(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id");
        }

        User user = userService.getFullUserById(bean.getId());

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        

        user.getJobFeatures().remove(feature);

        userService.updateUser(user);

        initUserFeatures(bean.getId());
        
        return "/features/showUserFeatures.xhtml?faces-redirect=true&id=" + bean.getId();
    }
    
}
