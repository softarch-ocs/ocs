
package controllers;

import DTO.statistics.CountJobsByAgeDTO;
import DTO.statistics.CountJobsByAreaDTO;
import DTO.statistics.CountJobsByGenderDTO;
import com.sun.xml.rpc.processor.generator.nodes.JaxRpcMappingTagNames;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.jobs.StatisticsService;

@ManagedBean
@ViewScoped
public class StatisticsController {
    private StatisticsService statisticsService;
    private String areaData, genderData;
    private String yearData;
    
    public StatisticsController(StatisticsService statisticsService) {
        if( statisticsService == null ){
            throw new IllegalArgumentException("statisticsServices");
        }
        
        this.statisticsService = statisticsService;
    }

    public StatisticsController() {
        this(new StatisticsService());
        areaData =  CountJobsByAreaDTO.dictionaryToString( statisticsService.getAreaData() );
        genderData = CountJobsByGenderDTO.dictionaryToString( statisticsService.getGenderData() );
        yearData =  CountJobsByAgeDTO.dictionaryToString( statisticsService.getAgeData() );
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
