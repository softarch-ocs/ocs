
package presentation.beans;

import data.entities.JobRequest;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ShowJobRequestsBean {
    private List<JobRequest> jobRequests;

    public List<JobRequest> getJobRequests() {
        return jobRequests;
    }

    public void setJobRequests(List<JobRequest> jobRequests) {
        this.jobRequests = jobRequests;
    }
    
}
