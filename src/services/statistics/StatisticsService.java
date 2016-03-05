package services.statistics;

import DTO.statistics.CountJobsByAgeDTO;
import DTO.statistics.CountJobsByAreaDTO;
import DTO.statistics.CountJobsByGenderDTO;
import data.dao.HibernateUtil;
import data.entities.Job;
import data.entities.User;
import data.entities.UsersJobs;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

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

    public List<CountJobsByGenderDTO> getGenderData() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<CountJobsByGenderDTO> data;
        try {

            data = session.createCriteria(User.class).
                    setProjection(Projections.projectionList().
                            add(Projections.rowCount(), "count").
                            add(Projections.groupProperty("gender"), "gender")
                    ).
                    setResultTransformer(new AliasToBeanResultTransformer(CountJobsByGenderDTO.class))
                    .list();
        } finally {
            tx.commit();
        }

        return data;
    }
    
    public List<CountJobsByAreaDTO> getAreaData() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<CountJobsByAreaDTO> data;
        try {

            data = session.createCriteria(UsersJobs.class, "usersJobs").
                    createAlias("usersJobs.job", "job").
                    createAlias("job.jobArea", "jobArea").
                    add(Restrictions.or(Restrictions.isNull("usersJobs.endTime"), Restrictions.ge("usersJobs.endTime", new Date()))).
                    setProjection(
                            Projections.projectionList().
                            add(Projections.rowCount(), "count").
                            add((Projections.groupProperty("jobArea.id"))).
                            add(Projections.property("jobArea.name"), "name")).
                    setResultTransformer(new AliasToBeanResultTransformer(CountJobsByAreaDTO.class)).
                    list();

        } finally {
            tx.commit();
        }

        return data;
    }

    public List<CountJobsByAgeDTO> getAgeData() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<CountJobsByAgeDTO> data;

        try {
            
            data = session.createCriteria(UsersJobs.class, "usersJobs").
                    createAlias("usersJobs.user", "user").
//                    createAlias("usersJobs.job", "job").
//                    createAlias("job.jobArea", "jobArea").
                    add(Restrictions.or(Restrictions.isNull("usersJobs.endTime"), Restrictions.ge("usersJobs.endTime", new Date()))).
                    setProjection(
                            Projections.projectionList().
                            add(Projections.rowCount(), "count").
                            add((Projections.groupProperty("user.id"))).
                            add(Projections.property("user.birthday"), "birthday")).
                    setResultTransformer( new AliasToBeanResultTransformer(CountJobsByAgeDTO.class) ).
                    list();
            

        } finally {
            tx.commit();
        }

        return data;
    }

}
