package DTO.statistics;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountJobsByAgeDTO {
    private Date birthday;
    private Long count;
    private static final Calendar calendar = Calendar.getInstance();
    private Integer year;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        calendar.setTime(birthday);
        this.year = calendar.get(Calendar.YEAR);
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
            if( !map.containsKey(dto.year) )
                map.put(dto.year, 0L);
            
            map.put(dto.year, map.get(dto.year) + dto.count);
        }
        return map;
    }    
}
