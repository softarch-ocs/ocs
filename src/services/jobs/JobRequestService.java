package services.jobs;

import data.dao.HibernateUtil;
import data.dao.TransactionContext;
import data.entities.Job;
import data.entities.JobFeature;
import data.entities.JobRequest;
import data.entities.JobRequest.Status;
import data.entities.User;
import data.entities.UsersJobs;
import external.services.soap.clients.BpelPostulateEmployeePortType;
import external.services.soap.clients.BpelPostulateEmployeeService;
import external.services.soap.clients.Employee;
import external.services.soap.clients.Gender;
import external.services.soap.clients.PostulateEmployeeRequestDto;
import external.services.soap.clients.PostulateEmployeeResponseDto;
import external.services.soap.clients.verify.VerifyEmployeesStatusRequestDto;
import external.services.soap.clients.verify.VerifyEmployeesStatusResponseDto;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import services.FeatureServices;
import services.exceptions.OcsPersistenceException;
import services.exceptions.OcsServiceException;
import services.exceptions.OcsValidationException;

public class JobRequestService {

    private SessionFactory sessionFactory;
    private static final String BPEL_USERNAME = "cobertura98";
    private static final String BPEL_PASSWORD = "cobertura98";

    public JobRequestService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public JobRequestService() {
        this(HibernateUtil.getSessionFactory());
    }
    
    public void postulateForExternalValidation(JobRequest jobRequest) {
        if (jobRequest == null) {
            throw new IllegalArgumentException("jobRequest cannot be null");
        }
        
        PostulateEmployeeRequestDto request = new PostulateEmployeeRequestDto();
        
        request.setUserName(BPEL_USERNAME);
        request.setPassword(BPEL_PASSWORD);
        
        User user = jobRequest.getUser();
        
        if (user == null) {
            throw new IllegalArgumentException("jobRequest has no user");
        }
        
        Employee employee = new Employee();
        
        employee.setDocument(user.getPersonalId());
        employee.setEmail(user.getEmail());
        employee.setFirstName(user.getFirstName());
        employee.setLastName(user.getLastName());
        employee.setGender(User.Gender.MALE.equals(user.getGender()) ? 
                Gender.MALE : Gender.FEMALE);
        
        request.setEmployee(employee);
        
        Job job = jobRequest.getJob();
        if (job == null) {
            throw new IllegalArgumentException("jobRequest has no job");
        }
        
        if (job.getJobFeatures() != null) {
            for(JobFeature feature : job.getJobFeatures()) {
                String skillTest = feature.getSkillTest();
                
                if (skillTest == null) continue;
                
                request.getFeatures().add(skillTest);
            }
        }
        
        try {
            bpelPostulateEmployeeOperation(request);
        } catch (Exception ex) {
            throw new OcsServiceException("Cannot perform postulate request", ex);
        }
    }

    public void createJobRequest(JobRequest jobRequest) {
        Session session = sessionFactory.getCurrentSession();

        try (TransactionContext ctx = new TransactionContext(session)) {
            session.save(jobRequest);
            ctx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex);
            throw new OcsPersistenceException(ex);
        }
        
        try {
            postulateForExternalValidation(jobRequest);
        } catch(OcsServiceException ex) {
            System.err.println("Unable to postulate for external review: " + 
                    jobRequest.getId());
            ex.printStackTrace();
        }
    }
    
    public VerifyEmployeesStatusResponseDto getExamsResults(JobRequest jobRequest) {
        VerifyEmployeesStatusRequestDto request = new VerifyEmployeesStatusRequestDto();
        
        User user = jobRequest.getUser();
        
        if (user == null) {
            throw new IllegalArgumentException("jobRequest has no user");
        }
        
        request.getEmployees().add(user.getPersonalId());
        try {
            return bpelVerifyEmployeesStatusOperation(request);
        } catch(Exception ex) {
            throw new OcsServiceException("Unable to fetch external results", ex);
        }
    }

    public List readAllJobRequest() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobRequest> jobRequests = null;
        try {
            tx = session.beginTransaction();
            jobRequests = session.createCriteria(JobRequest.class).setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN).addOrder(Order.asc("id")).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequests;
    }
    
    public List<JobRequest> readActiveJobRequests() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobRequest> jobRequests = null;
        try {
            tx = session.beginTransaction();
            jobRequests = session
                    .createCriteria(JobRequest.class)
                    .add(Restrictions.eq("status", JobRequest.Status.ACTIVE))
                    .setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("user.jobFeatures", FetchMode.JOIN)
                    .addOrder(Order.asc("id"))
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequests;
    }
    
    
    public List readAllJobRequestByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        List<JobRequest> jobRequests = null;
        try {
            tx = session.beginTransaction();
            jobRequests = session.createCriteria(JobRequest.class)
                    .add(Restrictions.eq("user", user))
                    .addOrder(Order.asc("id"))
                    .setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequests;
    }


    public JobRequest readJobRequest(int jobRequestId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        JobRequest jobRequest = null;
        try {
            tx = session.beginTransaction();
            jobRequest = (JobRequest) session.createCriteria(JobRequest.class)
                    .add(Restrictions.eq("id", jobRequestId))
                    .setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN)
                    .uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequest;
    }
    
    public JobRequest readJobRequest(User user, Job job, Status status) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        JobRequest jobRequest = null;
        try {
            tx = session.beginTransaction();
            jobRequest = (JobRequest) session.createCriteria(JobRequest.class)
                    .add(Restrictions.eq("user", user))
                    .add(Restrictions.eq("job", job))
                    .add(Restrictions.eq("status", status))
                    .setFetchMode("user", FetchMode.JOIN)
                    .setFetchMode("job", FetchMode.JOIN).uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

        return jobRequest;
    }

    public void changeStatusJobRequest(JobRequest jobRequest) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(jobRequest);

            if (jobRequest.getStatus() == JobRequest.Status.ACCEPTED) {
                UsersJobs userJobs = new UsersJobs();
                userJobs.setUser(jobRequest.getUser());
                userJobs.setJob(jobRequest.getJob());
                userJobs.setStartTime(new Date());
                userJobs.setEndTime(null);
                session.save(userJobs);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new OcsPersistenceException(e);
        }

    }

    public Map checkJobRequirements(User user, Job job) {

        FeatureServices featureServices = new FeatureServices(sessionFactory);

        List userFeatures = featureServices.readFeatures(user);
        List<JobFeature> jobFeatures = featureServices.readFeatures(job);
        Map<JobFeature, Boolean> features = new HashMap<>();
        
        for(JobFeature jf: jobFeatures){
            if(!userFeatures.contains(jf)){
                features.put(jf, Boolean.FALSE);
            }else{
                features.put(jf, Boolean.TRUE);
            }
        }
        
        return features;
    }

    public void checkAvailability(JobRequest jobRequest, Map features) {
                
        if (readJobRequest(jobRequest.getUser(), jobRequest.getJob(), jobRequest.getStatus()) != null) {
            throw new OcsValidationException(new OcsValidationException.ValidationItem(
                    "Sorry, you can't postulate again to this job"));
        }
        
        UserJobRelationServices userJobRelationServices = new UserJobRelationServices(sessionFactory);
        List<UsersJobs> jobs = userJobRelationServices.getUsersJobsByUserId(jobRequest.getUser().getId());
        
        for(UsersJobs job: jobs){
            if(job.getJob().getId() == jobRequest.getJob().getId()){
                  throw new OcsValidationException(new OcsValidationException.ValidationItem(
                    "Sorry, you are currently working in this job."));
            }
        }
        
        if(features.containsValue(Boolean.FALSE)){
            throw new OcsValidationException(new OcsValidationException.ValidationItem(
                    "Sorry, you don't fulfill the necessary requirements to postulate to this job"));
        }
    }

    private static PostulateEmployeeResponseDto bpelPostulateEmployeeOperation(PostulateEmployeeRequestDto request) {
        BpelPostulateEmployeeService service = new external.services.soap.clients.BpelPostulateEmployeeService();
        BpelPostulateEmployeePortType port = service.getBpelPostulateEmployeePort();
        return port.bpelPostulateEmployeeOperation(request);
    }

    private static VerifyEmployeesStatusResponseDto bpelVerifyEmployeesStatusOperation(external.services.soap.clients.verify.VerifyEmployeesStatusRequestDto request) {
        external.services.soap.clients.verify.BpelVerifyEmployeesStatusService service = new external.services.soap.clients.verify.BpelVerifyEmployeesStatusService();
        external.services.soap.clients.verify.BpelVerifyEmployeesStatusPortType port = service.getBpelVerifyEmployeesStatusPort();
        return port.bpelVerifyEmployeesStatusOperation(request);
    }
    
    
}
