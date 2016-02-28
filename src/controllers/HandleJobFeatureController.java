package controllers;

import data.entities.Job;
import data.entities.JobFeature;
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

    private Integer jobId;

    public HandleJobFeatureController() {
        super();
        featureServices = new FeatureServices();
        jobServices = new JobServices();
    }

    @PostConstruct
    public void initialize() {
        this.jobId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("jobId"));
    }

    public Integer getJobId() {
        return jobId;
    }

    public String joinFeatureJob(JobFeature feature) {
        
        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (jobId == null) {
            throw new IllegalArgumentException("jobId");
        }

        Job job = jobServices.readJob(Integer.parseInt(jobId + ""));

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }
        
        job.getJobFeatures().add(feature);

        jobServices.updateJob(job);

        return "/features/showJobFeatures.xhtml?faces-redirect=true&id=" + jobId;
    }

}
