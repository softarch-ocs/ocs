
package presentation.beans;

import data.entities.User;
import data.entities.UsersJobs;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UserHistoryBean implements Serializable {
    private List<UsersJobs> jobEntries;
    private User user;

    public List<UsersJobs> getJobEntries() {
        return jobEntries;
    }

    public void setJobEntries(List<UsersJobs> jobEntries) {
        this.jobEntries = jobEntries;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
