package controllers;

import data.entities.JobRequest;
import data.entities.User;
import data.entities.UsersJobs;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.UserService;
import services.jobs.JobRequestService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class MyJobHistoryController extends BaseController {

    private List<UsersJobs> jobEntries;
    private List<JobRequest> jobRequests;
    private UserJobRelationServices userJobService;
    private JobRequestService jobRequestService;

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
            jobEntries = Collections.unmodifiableList(
                    userJobService.getUsersJobsByUserId(user.getId()));
            jobRequests = Collections.unmodifiableList(
                    jobRequestService.readAllJobRequestByUser(user));
        }
    }

    public List<UsersJobs> getJobEntries() {
        return jobEntries;
    }

    public List<JobRequest> getJobRequests() {
        return jobRequests;
    }
}
