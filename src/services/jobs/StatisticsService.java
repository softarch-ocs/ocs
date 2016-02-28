/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.jobs;

import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.JobArea;
import data.entities.User;
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

    public Map<String,Integer> getAreaData(){
       Session session = sessionFactory.getCurrentSession();
       Transaction tx = session.beginTransaction();
       Map<String,Integer> data;
       try{
            List<JobArea> areas = session.createCriteria(JobArea.class).list();
            data = new HashMap<>();
            for(JobArea area : areas){
                data.put(
                        area.getName(), 
                        (Integer) session.createCriteria(Job.class).
                                setProjection(Projections.rowCount()).
                                setFetchMode("jobfeature", FetchMode.JOIN).
                                setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).
                                uniqueResult()
               );
            }
       } finally {
           tx.commit();
       }
       
       return data; 
    }
    
}
