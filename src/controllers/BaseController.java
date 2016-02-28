package controllers;

import data.entities.User;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import services.UserService;

public abstract class BaseController {
    protected final UserService userService;
    
    public BaseController(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("userService");
        }
        
        this.userService = userService;
    }
    
    public void requireRole(User.Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role can't be null");
        }
        
        User user = userService.getLoggedInUser();
        if (user == null || user.getRole().compareTo(role) < 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            String currentPath = externalContext.getRequestServletPath();
            
            // Flash messages won't work in this case, so let's use session
            externalContext.getSessionMap().put("POST_LOGIN_REDIRECT", currentPath);
            
            try {
                externalContext.redirect(
                        externalContext.getRequestContextPath() +  
                                "/users/login.xhtml");
            } catch(IOException ex) {
                // Wrap it in an unchecked exception
                throw new RuntimeException(ex);
            }
        }
    }
}
