package controllers;

import data.entities.User;
import services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import presentation.beans.LoginBean;

@ManagedBean
@ViewScoped
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("userService");
        }

        this.userService = userService;
    }

    public LoginController() {
        this(new UserService());
    }
    

    public String login(LoginBean loginBean) {
        if (loginBean == null) {
            throw new IllegalArgumentException("loginBean");
        }
        
        userService.login(loginBean.getEmail(), loginBean.getPassword());
        return "success";
    }
}
