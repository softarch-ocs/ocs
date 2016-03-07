package presentation.beans;

import data.entities.JobFeature;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PostulateJobBean {

    private Integer selectedJob;
    private String description;
    private Map<JobFeature, Boolean> features;

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
