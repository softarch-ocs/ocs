package controllers;

import data.entities.User;
import javax.faces.application.FacesMessage;
import services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.exceptions.OcsServiceException;

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
        try {
            userService.registerNewUser(user);
            userService.login(user);
            return "/index.xhtml";
        } catch(OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
            return "";
        }
    }
}
