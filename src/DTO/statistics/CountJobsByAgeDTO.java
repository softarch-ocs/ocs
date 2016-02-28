package DTO.statistics;

import static DTO.statistics.CountJobsByGenderDTO.listToMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountJobsByAgeDTO {
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
        return "CountJobsByAgeDTO{" + "year=" + bd + ", count=" + ctr + '}';
    }
    
    public static Map<Integer,Long> listToMap( List<CountJobsByAgeDTO> list ){
        Map<Integer,Long> map = new HashMap<>();
        for( CountJobsByAgeDTO dto : list ){
            if( !map.containsKey(dto.bd) )
                map.put(dto.bd, 0L);
            
            map.put(dto.bd, map.get(dto.bd) + dto.ctr);
        }
        return map;
    }
    
    public static String dictionaryToString( List<CountJobsByAgeDTO> list ){
        return listToMap(list).toString().replaceAll("=", ":");           
    }
    
    
}
