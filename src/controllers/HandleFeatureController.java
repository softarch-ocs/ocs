package controllers;

import data.entities.JobFeature;
import data.entities.User;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import presentation.beans.HandleFeatureBean;

import services.FeatureServices;
import services.UserService;

@ManagedBean
@RequestScoped
public class HandleFeatureController extends BaseController {

    private final FeatureServices featureServices;

    @ManagedProperty("#{handleFeatureBean}")
    private HandleFeatureBean bean;
    
    public HandleFeatureController() {
        super(new UserService());
        featureServices = new FeatureServices();
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }

    public Long getId() {
        return bean.getId();
    }

    public void initEdit(Long id) {
        bean.setId(id);

        if (id != null) {
            JobFeature tmpfeature = featureServices.getFeatureById(Integer.parseInt(id + ""));
            if (tmpfeature == null) {
                throw new IllegalArgumentException("jobFeature not found");
            }
            bean.setFeature(tmpfeature);
        }

        
    }

    public boolean isEditing() {
        return bean.getId() != null;
    }

    public JobFeature getEntity() {
        return bean.getFeature();
    }

    public void setEntity(JobFeature entity) {
        bean.setFeature(entity);
    }
    
    public HandleFeatureBean getBean() {
        return bean;
    }

    public void setBean(HandleFeatureBean bean) {
        this.bean = bean;
    }

    public String save() {
        JobFeature feature = bean.getFeature();
        if( feature.getSkillTest().equals("") ) {
            feature.setSkillTest(null);
        }
        if (isEditing()) {
            featureServices.updateFeature(feature);
        } else {
            featureServices.createFeature(feature);
        }

        return "/features/showAllFeatures.xhtml?faces-redirect=true";
    }

}
