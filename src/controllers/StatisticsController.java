
package controllers;

import DTO.statistics.CountJobsByAgeDTO;
import DTO.statistics.CountJobsByAreaDTO;
import DTO.statistics.CountJobsByGenderDTO;
import com.sun.xml.rpc.processor.generator.nodes.JaxRpcMappingTagNames;
import data.entities.User;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
        areaData =  CountJobsByAreaDTO.dictionaryToString( statisticsService.getAreaData() );
        genderData = CountJobsByGenderDTO.dictionaryToString( statisticsService.getGenderData() );
        yearData =  CountJobsByAgeDTO.dictionaryToString( statisticsService.getAgeData() );
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
