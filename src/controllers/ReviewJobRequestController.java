package controllers;

import data.entities.JobFeature;
import data.entities.JobRequest;
import data.entities.User;
import external.services.soap.clients.verify.ResultDto;
import external.services.soap.clients.verify.TestResultDto;
import external.services.soap.clients.verify.VerifyEmployeesStatusResponseDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import presentation.beans.ReviewJobRequestBean;
import services.UserService;
import services.exceptions.OcsServiceException;
import services.jobs.JobRequestService;
import services.jobs.JobServices;

@ManagedBean
@RequestScoped
public class ReviewJobRequestController extends BaseController {

    private JobRequestService jobRequestService;
    private JobServices jobServices;

    @ManagedProperty("#{reviewJobRequestBean}")
    private ReviewJobRequestBean bean;

    public ReviewJobRequestController(JobRequestService jobRequestService,
            JobServices jobServices,
            UserService userService) {

        super(userService);
        if (jobRequestService == null) {
            throw new IllegalArgumentException("jobRequest");
        }

        this.jobRequestService = jobRequestService;
        this.jobServices = jobServices;
    }

    public ReviewJobRequestController() {
        this(new JobRequestService(), new JobServices(), new UserService());
    }

    @PostConstruct
    public void initialize() {
        requireRole(User.Role.ADMIN);
    }

    public ReviewJobRequestBean getBean() {
        return bean;
    }

    public void setBean(ReviewJobRequestBean bean) {
        this.bean = bean;
    }

    public JobRequest getJobRequest() {
        return bean.getJobRequest();
    }

    public void initJobRequest(int jobRequestID) {
        bean.setJobRequest(jobRequestService.readJobRequest(jobRequestID));
        if (bean.getJobRequest() == null) {
            bean.setJobRequest(new JobRequest());
        }
        
        VerifyEmployeesStatusResponseDto result = 
                jobRequestService.getExamsResults(bean.getJobRequest());
        
        List<TestResultDto> skillExams = new ArrayList<>();
        List<TestResultDto> physicalExams = new ArrayList<>();
        JobRequest jobRequest = bean.getJobRequest();
        
        if (!result.getSkillResults().isEmpty()) {
            ResultDto resultDto = result.getSkillResults().get(0);
            
            assert jobRequest.getUser().getPersonalId().equals(resultDto.getDocument());
            
            Set<String> jobTests = new HashSet<>();
            List<JobFeature> jobFeatures = jobServices.readJobFeatures(
                    jobRequest.getJob().getId());
            
            for (JobFeature feature : jobFeatures) {
                if (feature.getSkillTest() == null) continue;
                jobTests.add(feature.getSkillTest());
            }
            
            List<TestResultDto> tests = resultDto.getTests();
            if (tests == null) {
                tests = new ArrayList<>();
            }
            
            for(TestResultDto test : tests) {
                if (!jobTests.contains(test.getName())) continue;
                skillExams.add(test);
            }
        }
        
        if (!result.getPhysicalResults().isEmpty()) {
            ResultDto resultDto = result.getPhysicalResults().get(0);
            
            assert jobRequest.getUser().getPersonalId().equals(resultDto.getDocument());
            
            physicalExams = resultDto.getTests();
            if (physicalExams == null) {
                physicalExams = new ArrayList<>();
            }
        }
        
        bean.setSkillsExamResults(skillExams);
        bean.setPhysicalExamResults(physicalExams);
    }

    public String changeJobRequestStatus() {
        try {
            jobRequestService.changeStatusJobRequest(bean.getJobRequest());
            return "/requests/showRequests.xhtml";
        } catch (OcsServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Oops, we had an error processing your request"));
            return "";
        }
    }

}
