package controllers;

import data.entities.JobFeature;
import data.entities.User;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import services.FeatureServices;
import services.UserService;

@ManagedBean
@ViewScoped
public class HandleUserFeatureController {

    private final FeatureServices featureServices;
    private final UserService userServices;

    private Integer id;

    public HandleUserFeatureController() {
        super();
        featureServices = new FeatureServices();
        userServices = new UserService();
    }

    @PostConstruct
    public void initialize() {
        String ret = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (ret == null) {
            throw new IllegalArgumentException("id not initilizated: HandleUserFeatureController: initialize()");
        }
        this.id = Integer.parseInt( ret );
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
        
        User user = userServices.getUserById(Integer.parseInt(id + ""));
        
        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        List<JobFeature> features = featureServices.readFeatures(user);
        if (features == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return features.contains(feature);
        
    }

    public void joinFeatureUser(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        User user = userServices.getFullUserById(Integer.parseInt(id + ""));

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        user.getJobFeatures().add(feature);

        userServices.updateUser(user);

        //return "/features/showUserFeatures.xhtml?faces-redirect=true&id=" + id;
    }
    
    public String deleteFeatureUser(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        User user = userServices.getFullUserById(Integer.parseInt(id + ""));

        if (user == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        
        user.getJobFeatures().remove(feature);

        userServices.updateUser(user);

        return "/features/showUserFeatures.xhtml?faces-redirect=true&id=" + id;
    }

}
