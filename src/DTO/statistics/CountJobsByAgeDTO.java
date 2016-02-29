package DTO.statistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountJobsByAgeDTO {
    private Integer birthday;
    private Long count;

    public Integer getBd() {
        return birthday;
    }

    public void setBd(Integer bd) {
        this.birthday = bd;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountJobsByAgeDTO{" + "year=" + birthday + ", count=" + count + '}';
    }
    
    public static Map<Integer,Long> listToMap( List<CountJobsByAgeDTO> list ){
        Map<Integer,Long> map = new HashMap<>();
        for( CountJobsByAgeDTO dto : list ){
            if( !map.containsKey(dto.birthday) )
                map.put(dto.birthday, 0L);
            
            map.put(dto.birthday, map.get(dto.birthday) + dto.count);
        }
        return map;
    }
    
    public static String dictionaryToString( List<CountJobsByAgeDTO> list ){
        return listToMap(list).toString().replaceAll("=", ":");           
    }
    
    
}
