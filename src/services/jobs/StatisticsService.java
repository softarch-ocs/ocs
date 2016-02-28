/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.jobs;

import DTO.statistics.JobYearDTO;
import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.JobArea;
import data.entities.User;
import data.entities.UsersJobs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

/**
 *
 * @author Felipe
 */
public class StatisticsService {
    private SessionFactory sessionFactory;

    public StatisticsService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public StatisticsService() {
        this(HibernateUtil.getSessionFactory());
    }

    public Map<String,Long> getAreaData(){
       Session session = sessionFactory.getCurrentSession();
       Transaction tx = session.beginTransaction();
       Map<String,Long> data;
       try{
            List<JobArea> areas = session.createCriteria(JobArea.class).list();
            data = new HashMap<>();
            for(JobArea area : areas){
                data.put(
                        area.getName(), 
                        (Long) session.createCriteria(Job.class).
                                createAlias("jobArea", "jobArea").
                                setProjection(Projections.rowCount()).
                                add(Restrictions.like("jobArea.name", area.getName())).
                                uniqueResult()
               );
            }
       } finally {
           tx.commit();
       }
       
       return data; 
    }
    
    public Map<String,Long> getGenderData(){
       Session session = sessionFactory.getCurrentSession();
       Transaction tx = session.beginTransaction();
       Map<String,Long> data;
       String male = "male", female = "female";

       try{
           data = new HashMap<>();
           data.put( male, 
                            (Long) session.createCriteria(UsersJobs.class).
                                   setProjection(Projections.rowCount()).
                                   createAlias("user", "user").
                                   add(Restrictions.eq("user.gender", User.Gender.MALE)).
                                   uniqueResult()
                   );
           
           data.put( female, 
                            (Long) session.createCriteria(UsersJobs.class).
                                   setProjection(Projections.rowCount()).
                                   createAlias("user", "user").
                                   add(Restrictions.eq("user.gender", User.Gender.FEMALE)).
                                   uniqueResult()
                   );
     
       } finally {
           tx.commit();
       }
       
       return data; 
    }
    
    
    
     public List<JobYearDTO> getAgeData(){
       Session session = sessionFactory.getCurrentSession();
       Transaction tx = session.beginTransaction();
       List<JobYearDTO> data;
      

       try{
           
           data = session.createQuery("select year(birthday) as bd, count(*) as ctr from User group by year(birthday)").
                   setResultTransformer(new AliasToBeanResultTransformer(JobYearDTO.class))
                  .list(); 

       } finally {
           tx.commit();
       }
       
       return data; 
    }
    
}
