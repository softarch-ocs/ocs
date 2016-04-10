
package controllers;

import DTO.statistics.CountJobsByAgeDTO;
import DTO.statistics.CountJobsByAreaDTO;
import DTO.statistics.CountJobsByGenderDTO;
import data.entities.User;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.codehaus.jackson.map.ObjectMapper;
import presentation.beans.StatisticsBean;
import services.UserService;
import services.statistics.StatisticsService;


@ManagedBean
@RequestScoped
public class StatisticsController extends BaseController {
    private StatisticsService statisticsService;
    
    @ManagedProperty("#{statisticsBean}")
    private StatisticsBean bean;

    public StatisticsBean getBean() {
        return bean;
    }

    public void setBean(StatisticsBean bean) {
        this.bean = bean;
    }
    
        
    public StatisticsController(StatisticsService statisticsService, UserService userService) {
        super(userService);
        if( statisticsService == null ){
            throw new IllegalArgumentException("statisticsServices");
        }
        
        this.statisticsService = statisticsService;
    }

    public StatisticsController() {
        this(new StatisticsService(),new UserService());
        
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
        try {
            ObjectMapper mapper = new ObjectMapper();
            bean.setAreaData( mapper.writeValueAsString( CountJobsByAreaDTO.listToMap( statisticsService.getAreaData() ) ) );
            bean.setGenderData( mapper.writeValueAsString( CountJobsByGenderDTO.listToMap( statisticsService.getGenderData() ) ) );        
            bean.setYearData( mapper.writeValueAsString( CountJobsByAgeDTO.listToMap( statisticsService.getAgeData() ) ) );
        } catch (IOException ex) {
             FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
        }
    }
}
