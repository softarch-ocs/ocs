package controllers;

import data.entities.User;
import services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("userService");
        }

        this.userService = userService;
    }

    public UserController() {
        this(new UserService());
    }
    
    public User getCurrentUser() {
        return userService.getLoggedInUser();
    }
    
    public boolean hasRole(User.Role role) {
        User user = getCurrentUser();
        return user != null && user.getRole().compareTo(role) >= 0;
    }
}
