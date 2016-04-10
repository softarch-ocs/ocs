package presentation.beans;

import data.entities.JobFeature;
import data.entities.JobRequest;
import data.entities.User;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PostulateJobBean implements Serializable{

    private Integer selectedJob;
    private String description;
    private Map<JobFeature, Boolean> features;
    private User user;
    private List jobs;
    private JobRequest jobRequest;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List getJobs() {
        return jobs;
    }

    public void setJobs(List jobs) {
        this.jobs = jobs;
    }

    public JobRequest getJobRequest() {
        return jobRequest;
    }

    public void setJobRequest(JobRequest jobRequest) {
        this.jobRequest = jobRequest;
    }

    public Integer getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Integer selectedJob) {
        this.selectedJob = selectedJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<JobFeature, Boolean> getFeatures() {
        return features;
    }

    public void setFeatures(Map features) {
        this.features = features;
    }

}
