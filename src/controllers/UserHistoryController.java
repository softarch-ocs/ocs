package controllers;

import data.entities.User;
import data.entities.UsersJobs;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.UserService;
import services.jobs.UserJobRelationServices;

@ManagedBean
@ViewScoped
public class UserHistoryController extends BaseController {
    private List<UsersJobs> jobEntries;
    private UserJobRelationServices userJobService;
    private User user;
    
    public UserHistoryController(UserService userService, 
            UserJobRelationServices userJobService) {
        super(userService);
        
        if (userJobService == null) {
            throw new IllegalArgumentException("userJobService");
        }
        
        this.userJobService = userJobService;
    }
    
    public UserHistoryController() {
        this(new UserService(), new UserJobRelationServices());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        
        Integer userId = Integer.parseInt(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("userId"));
        
        user = userService.getFullUserById(userId);
        updateJobEntries();
    }
    
    public List<UsersJobs> getJobEntries() {
        return jobEntries;
    }
    
    public User getUser() {
        return user;
    }
    
    public String deleteJobEntry(int id) {
        userJobService.deleteUserJobRelation(userJobService.getUsersJobsById(id));
        updateJobEntries();
        
        return "";
    }
    
    private void updateJobEntries() {
        jobEntries = Collections.unmodifiableList(
                userJobService.getUsersJobsByUserId(user.getId()));
    }
}
