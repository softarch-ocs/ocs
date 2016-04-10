package presentation.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class StatisticsBean implements Serializable{
    private String areaData, genderData;
    private String yearData;

   
    public String getAreaData() {
        return areaData;
    }

    public void setAreaData(String areaData) {
        this.areaData = areaData;
    }

    public String getGenderData() {
        return genderData;
    }

    public void setGenderData(String genderData) {
        this.genderData = genderData;
    }

    public String getYearData() {
        return yearData;
    }

    public void setYearData(String yearData) {
        this.yearData = yearData;
    }
    
    
}
