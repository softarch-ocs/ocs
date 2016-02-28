package controllers;

import javax.faces.application.FacesMessage;
import services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
        
        
        if (userService.login(loginBean.getEmail(), loginBean.getPassword())) {
            // Consume this redirect
            String target = (String)FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap()
                    .remove("POST_LOGIN_REDIRECT");
            
            if (target == null) {
                target = "/index.xhtml";
            }
            
            return target;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Invalid credentials"));
            return "";
        }
    }
    
    public String logout(){
        userService.logout();
        return "/index.xhtml";
    }
}
