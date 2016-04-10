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
public class ShowFeaturesBean implements Serializable {
    
    private List<JobFeature> features;
    
    public ShowFeaturesBean(){
        
    }
    
    public List<JobFeature> getFeatures() {
        return features;
    }
    
    public void setFeatures( List<JobFeature> features ) {
        this.features = features;
    }
    
}
