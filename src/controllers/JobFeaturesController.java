package controllers;

import data.entities.Job;
import data.entities.JobFeature;
import data.entities.User;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.JobFeaturesBean;
import services.FeatureServices;
import services.UserService;
import services.jobs.JobServices;

@ManagedBean
@RequestScoped
public class JobFeaturesController extends BaseController {

    private final FeatureServices featureService;
    private final JobServices jobService;

    @ManagedProperty("#{jobFeaturesBean}")
    private JobFeaturesBean bean;

    public JobFeaturesBean getBean() {
        return bean;
    }

    public void setBean(JobFeaturesBean bean) {
        this.bean = bean;
    }

    public List<JobFeature> getJobFeatures() {
        return bean.getJobFeatures();
    }

    public void setJobFeatures(List<JobFeature> jobFeatures) {
        bean.setJobFeatures(jobFeatures);
    }

    public JobFeaturesController() {
        super(new UserService());
        featureService = new FeatureServices();
        jobService = new JobServices();
    }

    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        String ret = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (bean.getId() == null && ret == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:noId");
        }
        if(bean.getId() == null){
            bean.setId(Integer.parseInt(ret));
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id ShowFeaturesController:id-null");
        }

        initJobFeatures(bean.getId());
    }

    public void initJobFeatures(Integer id) {

        Job job = jobService.readJob(id);
        if (job == null) {
            throw new IllegalArgumentException("user");
        }

        bean.setJobFeatures(featureService.readFeatures(job));

    }

    public Integer getJobId() {
        return bean.getId();
    }

    public boolean isFeatureInJob(JobFeature feature) {

        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobService.readJob(bean.getId());

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }

        bean.setJobFeatures(featureService.readFeatures(job));
        if (bean.getJobFeatures() == null) {
            throw new IllegalArgumentException("features can not be load");
        }
        return bean.getJobFeatures().contains(feature);

    }

    public void joinFeatureJob(JobFeature feature) {

        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobService.readJob(bean.getId());

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }

        if (isFeatureInJob(feature)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("The feature is already in the job"));

            return;
        }

        job.getJobFeatures().add(feature);

        jobService.updateJob(job);
        initJobFeatures(bean.getId());
    }

    public String deleteFeatureJob(JobFeature feature) {

        if (feature == null) {
            throw new IllegalArgumentException("feature");
        }
        if (bean.getId() == null) {
            throw new IllegalArgumentException("id");
        }

        Job job = jobService.readJob(bean.getId());

        if (job == null) {
            throw new IllegalArgumentException("job does not exist");
        }

        job.getJobFeatures().remove(feature);

        jobService.updateJob(job);

        initJobFeatures(bean.getId());
        return "/features/showJobFeatures.xhtml?faces-redirect=true&id=" + bean.getId();
    }

}
