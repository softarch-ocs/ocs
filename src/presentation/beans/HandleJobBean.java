
package presentation.beans;

import data.entities.Job;
import data.entities.JobArea;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HandleJobBean implements Serializable{
    private Job entity;
    private Long id;
    private List<JobArea> areas;
    
    public boolean isEditing(){
        return id != null;
    }

    public Job getEntity() {
        return entity;
    }

    public void setEntity(Job entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JobArea> getAreas() {
        return areas;
    }

    public void setAreas(List<JobArea> areas) {
        this.areas = areas;
    }
    
    
}
