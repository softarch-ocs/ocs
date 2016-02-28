package controllers;

import data.entities.Job;
import data.entities.JobFeature;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import services.FeatureServices;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class HandleJobFeatureController {

    private final FeatureServices featureServices;
    private final JobServices jobServices;

    private Integer id;

    public HandleJobFeatureController() {
        super();
        featureServices = new FeatureServices();
        jobServices = new JobServices();
    }

    @PostConstruct
    public void initialize() {
        String ret = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (ret == null) {
            throw new IllegalArgumentException("id not initilizated: HandleJobFeatureController: initialize()");
        }
        this.id = Integer.parseInt( ret );
    }

    public Integer getJobId() {
        return id;
    }
    
    public boolean isFeatureInJob( JobFeature feature ){
        
        if(feature == null){
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }
        
        Job job = jobServices.readJob(Integer.parseInt(id + ""));
        
        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        List<JobFeature> features = featureServices.readFeatures(job);
        if (features == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return features.contains(feature);
        
    }

    public String joinFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobServices.readJob(Integer.parseInt(id + ""));

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        job.getJobFeatures().add(feature);

        jobServices.updateJob(job);

        return "/features/showJobFeatures.xhtml?faces-redirect=true&id=" + id;
    }
    
    public String deleteFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobServices.readJob(Integer.parseInt(id + ""));

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        job.getJobFeatures().remove(feature);

        jobServices.updateJob(job);

        return "/features/showJobFeatures.xhtml?faces-redirect=true&id=" + id;
    }

}
