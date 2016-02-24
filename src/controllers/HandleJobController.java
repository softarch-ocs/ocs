package controllers;
import data.entities.Job;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class HandleJobController {
    private Job entity;
    private Long id;
    private String lok;

    public HandleJobController(){
        super();
        entity = new Job();
    }

    public Long getId() {
        return id;
    }

    public void initEdit(Long id){
        System.out.println("Init edit");
        this.id = id;
    }

    public boolean isEditing(){
        System.out.println("Is editing");
        return id != null;
    }

    public Job getEntity() {
        return entity;
    }

    public void setEntity(Job entity){
        this.entity = entity;
    }

    public void save(){
        //TODO: Set logic of saving or updating
        System.out.println("here");
    }
}
