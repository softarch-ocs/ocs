package controllers;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class HandleJobController {
    //private JobEntity entity;
    private Long id;

    public Long getId() {
        return id;
    }

    public void initEdit(Long id){
        System.out.println("Init edit");
        this.id = id;
        //throw new RuntimeException("kljsdasd");
    }



    public boolean isEditing(){
        System.out.println("Is editing");
        return id != null;
    }


    /*public JobEntity getEntity() {
        return entity;
    }*/



}
