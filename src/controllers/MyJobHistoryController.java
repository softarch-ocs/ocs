package controllers;

import data.entities.JobRequest;
import data.entities.User;
import data.entities.UsersJobs;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import presentation.beans.MyJobHistoryBean;
import services.UserService;
import services.jobs.JobRequestService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@RequestScoped
public class MyJobHistoryController extends BaseController {

    private UserJobRelationServices userJobService;
    private JobRequestService jobRequestService;
    
    @ManagedProperty("#{myJobHistoryBean}")
    private MyJobHistoryBean bean;

    public MyJobHistoryController(UserService userService,
            UserJobRelationServices userJobService, JobRequestService jobRequestService) {
        super(userService);

        if (userJobService == null) {
            throw new IllegalArgumentException("userJobService");
        } else if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequestService");
        }

        this.userJobService = userJobService;
        this.jobRequestService = jobRequestService;
    }

    public MyJobHistoryController() {
        this(new UserService(), new UserJobRelationServices(), new JobRequestService());
    }

    @PostConstruct
    public void initialize() {
        requireRole(User.Role.USER);

        User user = userService.getLoggedInUser();

        if (user != null) {
            bean.setJobEntries(Collections.unmodifiableList(
                    userJobService.getUsersJobsByUserId(user.getId())));
            bean.setJobRequests(Collections.unmodifiableList(
                    jobRequestService.readAllJobRequestByUser(user)));
        }
    }

    public MyJobHistoryBean getBean() {
        return bean;
    }

    public void setBean(MyJobHistoryBean bean) {
        this.bean = bean;
    }

    public List<UsersJobs> getJobEntries() {
        return bean.getJobEntries();
    }

    public List<JobRequest> getJobRequests() {
        return bean.getJobRequests();
    }
}
