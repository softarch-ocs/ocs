package controllers;

import data.entities.User;
import services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RegisterUserController {
    private UserService userService;
    private User user;

    public RegisterUserController(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("userService");
        }

        this.userService = userService;
        user = new User();
        user.setRole(User.Role.USER.value());
    }

    public RegisterUserController() {
        this(new UserService());
    }

    public User getUser() {
        return user;
    }

    public String register() {
        userService.registerNewUser(user);
        return "success";
    }
}
