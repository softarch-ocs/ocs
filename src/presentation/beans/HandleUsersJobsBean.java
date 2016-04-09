package presentation.beans;

import data.entities.Job;
import data.entities.User;
import data.entities.UsersJobs;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HandleUsersJobsBean implements Serializable {
    private UsersJobs entity;
    private Integer id;
    private List<Job> jobs;
    private User user;
    
    public HandleUsersJobsBean() {
        this.entity = new UsersJobs();
    }

    public UsersJobs getEntity() {
        return entity;
    }

    public void setEntity(UsersJobs entity) {
        this.entity = entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
