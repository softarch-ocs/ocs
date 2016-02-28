/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Felipe
 */
public class JobYearDTO {
    private Integer bd;
    private Long ctr;

    public Integer getBd() {
        return bd;
    }

    public void setBd(Integer bd) {
        this.bd = bd;
    }

    public Long getCount() {
        return ctr;
    }

    public void setCount(Long count) {
        this.ctr = count;
    }

    @Override
    public String toString() {
        return "JobYearDTO{" + "year=" + bd + ", count=" + ctr + '}';
    }
    
    public static Map<Integer,Long> listToMap( List<JobYearDTO> list ){
        Map<Integer,Long> map = new HashMap<>();
        for( JobYearDTO dto : list ){
            if( !map.containsKey(dto.bd) )
                map.put(dto.bd, 0L);
            
            map.put(dto.bd, map.get(dto.bd) + dto.ctr);
        }
        return map;
    }
    
    
}
