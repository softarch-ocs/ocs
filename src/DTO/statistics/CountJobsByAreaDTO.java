package DTO.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountJobsByAreaDTO {
    private String name;
    private Long count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    
    

    public static Map<String,Long> listToMap( List<CountJobsByAreaDTO> list ){
        Map<String,Long> map = new HashMap<>();
        for( CountJobsByAreaDTO dto : list ){
            if( !map.containsKey(dto.name) )
                map.put(dto.name, 0L);
            
            map.put(dto.name,  map.get(dto.name) + dto.count );
        }
        return map;
    }
}
