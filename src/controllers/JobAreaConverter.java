/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import data.entities.JobArea;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import services.jobs.JobServices;

/**
 *
 * @author Felipe
 */
@ManagedBean
@RequestScoped

public class JobAreaConverter implements Converter{
    
    private JobServices jobServices;

    public JobAreaConverter(JobServices jobServices) {
        this.jobServices = jobServices;
    }

    public JobAreaConverter() {
        this(new JobServices());
    }
    
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            return jobServices.getJobAreaById(Integer.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid JobArea name", submittedValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof JobArea) {
            return String.valueOf(((JobArea) modelValue).getId());
        } else {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", modelValue)));
        }
    }
    
}
