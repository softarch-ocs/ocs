
package DTO.statistics;

import static DTO.statistics.CountJobsByAreaDTO.listToMap;
import data.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountJobsByGenderDTO {
    private User.Gender gender;
    private Long count;

    public User.Gender getGender() {
        return gender;
    }

    public void setGender(User.Gender gender) {
        this.gender = gender;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    
    public static Map<String,Long> listToMap( List<CountJobsByGenderDTO> list ){
        Map<String,Long> map = new HashMap<>();
        for( CountJobsByGenderDTO dto : list ){
            if( !map.containsKey(dto.gender.name()) )
                map.put(dto.gender.name(), 0L);
            
            map.put(dto.gender.name(), map.get(dto.gender.name()) + dto.count);
        }
        return map;
    }
    
    public static String dictionaryToString( List<CountJobsByGenderDTO> list ){
        return listToMap(list).toString().replaceAll("=", ":");           
    }
}
