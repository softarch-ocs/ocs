package controllers;

import data.entities.Job;
import data.entities.JobFeature;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import services.FeatureServices;
import services.jobs.JobServices;

@ManagedBean
@ViewScoped
public class HandleFeatureController {
    
    private final FeatureServices featureServices;
    private final JobServices jobServices;
    
    private JobFeature feature;
    
    private Long id;

    public HandleFeatureController(){
        super();
        feature = new JobFeature();
        featureServices = new FeatureServices();
        jobServices = new JobServices();
        
        feature.setName("name placeholder");
        feature.setDescription("description placeholder");
    }

    public Long getId() {
        return id;
    }

    public void initEdit( Long id ){
        this.id = id;
        
        feature = new JobFeature();
        feature.setName("name placeholder");
        feature.setDescription("description placeholder");
            
        if( id != null ){
            JobFeature tmpfeature = featureServices.getFeatureById( Integer.parseInt( id + "" ) );
            if( tmpfeature != null ){
                feature = tmpfeature;
            }
        }
        
    }

    public boolean isEditing(){
        return id != null;
    }

    public JobFeature getEntity() {
        return feature;
    }

    public void setEntity( JobFeature entity ){
        this.feature = entity;
    }

    public void save(){

        if( isEditing() ){
            featureServices.updateFeature( feature );
        }else{
            featureServices.createFeature( feature );
        }
    }
    
    public void joinFeatureJob( JobFeature feature, Long jobId ){
        
        if( feature != null && jobId != null ){
            Job job = jobServices.readJob( Integer.parseInt( jobId + "" ) );
            if( job != null ){
                featureServices.addFeature( job, feature );
            }
        }
        
    }
    
}
