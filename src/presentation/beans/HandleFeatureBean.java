/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.beans;

import data.entities.JobFeature;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HandleFeatureBean implements Serializable {
    private JobFeature feature;
    private Long id;
    
    public HandleFeatureBean(){
        feature = new JobFeature();
        feature.setName("name placeholder");
        feature.setDescription("description placeholder");
    }
    
    public JobFeature getFeature(){
        return feature;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setFeature( JobFeature feature ){
        this.feature = feature;
    }
    
    public void setId( Long id ){
        this.id = id;
    }
    
}
