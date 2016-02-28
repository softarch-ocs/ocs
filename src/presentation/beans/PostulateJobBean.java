package presentation.beans;

import controllers.PostulateJobController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PostulateJobBean {

    private Integer selectedJob;
    private String description;
    private String features;

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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

}
