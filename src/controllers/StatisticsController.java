/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTO.statistics.JobYearDTO;
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
    private Map<String,Long> areaData, genderData;
    private List<JobYearDTO> yearData;
    
    public StatisticsController(StatisticsService statisticsService) {
        if( statisticsService == null ){
            throw new IllegalArgumentException("statisticsServices");
        }
        
        this.statisticsService = statisticsService;
    }

    public StatisticsController() {
        this(new StatisticsService());
        areaData = statisticsService.getAreaData();
        genderData = statisticsService.getGenderData();
        yearData = statisticsService.getAgeData();
    }

    public List<JobYearDTO> getYearData() {
        return yearData;
    }

    
    public Map<String, Long> getAreaData() {
        return areaData;
    }    

    public Map<String, Long> getGenderData() {
        return genderData;
    }
    
    
    
}
