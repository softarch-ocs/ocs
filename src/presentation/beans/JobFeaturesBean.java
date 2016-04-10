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
public class JobFeaturesBean implements Serializable {

    private List<JobFeature> jobFeatures;

    private Integer id;

    
    public JobFeaturesBean() {
    }

    public List<JobFeature> getJobFeatures() {
        return jobFeatures;
    }

    public void setJobFeatures(List<JobFeature> jobFeatures) {
        this.jobFeatures = jobFeatures;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
