
package controllers;

import DTO.statistics.CountJobsByAgeDTO;
import DTO.statistics.CountJobsByAreaDTO;
import DTO.statistics.CountJobsByGenderDTO;
import com.sun.xml.rpc.processor.generator.nodes.JaxRpcMappingTagNames;
import data.entities.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.codehaus.jackson.map.ObjectMapper;
import services.UserService;
import services.statistics.StatisticsService;


@ManagedBean
@ViewScoped
public class StatisticsController extends BaseController {
    private StatisticsService statisticsService;
    private String areaData, genderData;
    private String yearData;
    
    public StatisticsController(StatisticsService statisticsService, UserService userService) {
        super(userService);
        if( statisticsService == null ){
            throw new IllegalArgumentException("statisticsServices");
        }
        
        this.statisticsService = statisticsService;
    }

    public StatisticsController() {
        this(new StatisticsService(),new UserService());
        try {
            ObjectMapper mapper = new ObjectMapper();
            areaData =  mapper.writeValueAsString( CountJobsByAreaDTO.listToMap( statisticsService.getAreaData() ) );
            genderData = mapper.writeValueAsString( CountJobsByGenderDTO.listToMap( statisticsService.getGenderData() ) );        
            yearData = mapper.writeValueAsString( CountJobsByAgeDTO.listToMap( statisticsService.getAgeData() ) );
        } catch (IOException ex) {
             FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("Oops, we had an error processing your request"));
        }
    }
    
    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }

    public String getYearData() {
        return yearData;
    }

    
    public String getAreaData() {
        return areaData;
    }    

    public String getGenderData() {
        return genderData;
    }
    
    
    
}
