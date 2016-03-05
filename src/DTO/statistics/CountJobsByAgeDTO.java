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
    private Integer age;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        
        calendar.setTime(birthday);
        int year = calendar.get(Calendar.YEAR);
        
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        
        this.age = currentYear - year;
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
    
    public static Map<String,Long> listToMap( List<CountJobsByAgeDTO> list ){
        
             
        Map<String,Long> map = new HashMap<>();
        for( CountJobsByAgeDTO dto : list ){
            
            int i = dto.age;
            if( i % 10 != 0 )
                i = i/10;
            else
                i = i/10 - 1;
           
            String key = (i*10+1) + " - " + (i+1)*10;            
            
            if( !map.containsKey(key) )
                map.put(key, 0L);
            
            map.put(key, map.get(key) + dto.count);
        }
        return map;
    }    
}
