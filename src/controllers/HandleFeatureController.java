package controllers;

import data.entities.JobFeature;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import services.FeatureServices;

@ManagedBean
@ViewScoped
public class HandleFeatureController {

    private final FeatureServices featureServices;

    private JobFeature feature;

    private Long id;

    public HandleFeatureController() {
        super();
        feature = new JobFeature();
        featureServices = new FeatureServices();

        feature.setName("name placeholder");
        feature.setDescription("description placeholder");
    }

    public Long getId() {
        return id;
    }

    public void initEdit(Long id) {
        this.id = id;

        feature = new JobFeature();
        feature.setName("name placeholder");
        feature.setDescription("description placeholder");

        if (id != null) {
            JobFeature tmpfeature = featureServices.getFeatureById(Integer.parseInt(id + ""));
            if (tmpfeature == null) {
                throw new IllegalArgumentException("jobFeature not found");
            }
            feature = tmpfeature;

        }

        
    }

    public boolean isEditing() {
        return id != null;
    }

    public JobFeature getEntity() {
        return feature;
    }

    public void setEntity(JobFeature entity) {
        this.feature = entity;
    }

    public String save() {

        if (isEditing()) {
            featureServices.updateFeature(feature);
        } else {
            featureServices.createFeature(feature);
        }

        return "/features/showAllFeatures.xhtml?faces-redirect=true";
    }

}
