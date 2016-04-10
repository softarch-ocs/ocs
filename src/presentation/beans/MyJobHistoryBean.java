
package presentation.beans;

import data.entities.JobRequest;
import data.entities.UsersJobs;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MyJobHistoryBean {
    
    private List<UsersJobs> jobEntries;
    private List<JobRequest> jobRequests;

    public List<UsersJobs> getJobEntries() {
        return jobEntries;
    }

    public void setJobEntries(List<UsersJobs> jobEntries) {
        this.jobEntries = jobEntries;
    }

    public List<JobRequest> getJobRequests() {
        return jobRequests;
    }

    public void setJobRequests(List<JobRequest> jobRequests) {
        this.jobRequests = jobRequests;
    }
    
}
