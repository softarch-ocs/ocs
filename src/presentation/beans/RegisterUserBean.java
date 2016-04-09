package presentation.beans;

import data.entities.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class RegisterUserBean implements Serializable {
    private User user;
    
    public RegisterUserBean() {
        user = new User();
        user.setRole(User.Role.USER);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
