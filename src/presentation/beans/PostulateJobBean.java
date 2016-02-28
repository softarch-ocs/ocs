package presentation.beans;

import data.entities.JobFeature;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PostulateJobBean {

    private Integer selectedJob;
    private String description;
    private List<JobFeature> features;

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

    public List getFeatures() {
        return features;
    }

    public void setFeatures(List features) {
        this.features = features;
    }

}
