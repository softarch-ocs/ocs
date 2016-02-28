
package presentation.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PostulateJobBean {
    
    private Integer selectedJob;

    public Integer getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Integer selectedJob) {
        this.selectedJob = selectedJob;
    }
    
}
