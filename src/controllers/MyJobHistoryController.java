package controllers;

import data.entities.User;
import data.entities.UsersJobs;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.UserService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class MyJobHistoryController extends BaseController {
    private List<UsersJobs> jobEntries;
    private UserJobRelationServices userJobService;
    
    public MyJobHistoryController(UserService userService, 
            UserJobRelationServices userJobService) {
        super(userService);
        
        if (userJobService == null) {
            throw new IllegalArgumentException("userJobService");
        }
        
        this.userJobService = userJobService;
    }
    
    public MyJobHistoryController() {
        this(new UserService(), new UserJobRelationServices());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.USER);
        
        User user = userService.getLoggedInUser();
        
        if (user != null) {
            jobEntries = Collections.unmodifiableList(
                    userJobService.getUsersJobsByUserId(user.getId()));
        }
    }
    
    public List<UsersJobs> getJobEntries() {
        return jobEntries;
    }
}
