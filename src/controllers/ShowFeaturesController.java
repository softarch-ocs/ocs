package controllers;

import data.entities.JobFeature;
import data.entities.User;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import presentation.beans.ShowFeaturesBean;

import services.FeatureServices;
import services.UserService;

@ManagedBean
@RequestScoped
public class ShowFeaturesController extends BaseController {

    private final FeatureServices featureService;

    @ManagedProperty("#{showFeaturesBean}")
    private ShowFeaturesBean bean;

    public ShowFeaturesBean getBean() {
        return bean;
    }

    public void setBean(ShowFeaturesBean bean) {
        this.bean = bean;
    }

    public List<JobFeature> getFeatures() {
        return bean.getFeatures();
    }

    public void setFeatures(List<JobFeature> features) {
        bean.setFeatures(features);
    }

    public ShowFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();
    }

    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        bean.setFeatures(featureService.readAllFeatures());
    }

}
