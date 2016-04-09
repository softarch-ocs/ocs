package controllers;

import data.entities.User;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import services.UserService;

@ManagedBean
@RequestScoped
public class UserListController extends BaseController {
    private List<User> users;
    
    public UserListController(UserService userService) {
        super(userService);
    }
    
    public UserListController() {
        this(new UserService());
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        
        users = Collections.unmodifiableList(userService.getAllUsers());
    }
    
    public List<User> getUsers() {
        return users;
    }
}
