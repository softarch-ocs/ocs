package controllers;

import data.entities.User;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import services.UserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.RegisterUserBean;
import services.exceptions.OcsServiceException;

@ManagedBean
@RequestScoped
public class RegisterUserController {
    private UserService userService;
    
    @ManagedProperty("#{registerUserBean}")
    private RegisterUserBean bean;

    public RegisterUserBean getBean() {
        return bean;
    }

    public void setBean(RegisterUserBean bean) {
        this.bean = bean;
    }

    public RegisterUserController(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("userService");
        }

        this.userService = userService;
    }

    public RegisterUserController() {
        this(new UserService());
    }

    public User getUser() {
        return bean.getUser();
    }

    public String register() {
        User user = bean.getUser();
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
