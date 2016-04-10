/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.beans;

import data.entities.JobFeature;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UserFeaturesBean implements Serializable {
    
    private List<JobFeature> userFeatures;
    
    private Integer id;
    
    public UserFeaturesBean(){
    }
    
    public List<JobFeature> getUserFeatures(){
        return userFeatures;
    }
    
    public void setUserFeatures( List<JobFeature> userFeatures ){
        this.userFeatures = userFeatures;
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
}
