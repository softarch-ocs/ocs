/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTO.statistics.JobYearDTO;
import com.sun.xml.rpc.processor.generator.nodes.JaxRpcMappingTagNames;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import services.jobs.StatisticsService;

/**
 *
 * @author Felipe
 */

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
        areaData = statisticsService.getAreaData().toString().replaceAll("=", ":");
        genderData = statisticsService.getGenderData().toString().replaceAll("=", ":");
        yearData = JobYearDTO.listToMap( statisticsService.getAgeData() ).toString().replaceAll("=", ":");
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
