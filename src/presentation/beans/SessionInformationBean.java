package presentation.beans;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class SessionInformationBean {
    private Map<String, Object> sessionMap;
    private String sessionId;
    
    @PostConstruct 
    public void initialize() {
        sessionMap = 
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap();
        
        sessionMap.put("accessCount", getCounter() + 1);
        
        sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
    }
    
    public int getCounter() {
        return (Integer) sessionMap.getOrDefault("accessCount", 0);
    }
    
    public String getNodeName() {
        return System.getProperty("jboss.node.name");
    }
    
    public String getSessionId() {
        return sessionId;
    }
}
