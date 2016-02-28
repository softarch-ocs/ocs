package controllers;

import data.entities.User;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.UserService;

@ManagedBean
@ViewScoped
public class UserListController extends BaseController {
    private List<User> users;
    
    public UserListController(UserService userService) {
        super(userService);
        
        users = Collections.unmodifiableList(userService.getAllUsers());
    }
    
    public UserListController() {
        this(new UserService());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }
    
    public List<User> getUsers() {
        return users;
    }
}
