package external.services;

import data.dao.HibernateUtil;
import data.entities.JobFeature;
import data.entities.JobRequest;
import external.dto.EmployeeToEvaluateDto;
import external.dto.EmployeesToEvaluateDto;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import services.jobs.JobRequestService;


@WebService(serviceName = "GetEmployeesToEvaluate")
public class GetEmployeesToEvaluate {
    private JobRequestService jobRequestService;
    
    public GetEmployeesToEvaluate() {
        jobRequestService = new JobRequestService(
                HibernateUtil.getSessionFactory());
    }

    @WebMethod(operationName = "get")
    public EmployeesToEvaluateDto get(@WebParam(name = "userName") String userName, 
            @WebParam(name = "password") String password) {
        EmployeesToEvaluateDto result = new EmployeesToEvaluateDto();
        
        List<JobRequest> requests = 
                jobRequestService.readActiveJobRequests();
        List<EmployeeToEvaluateDto> employees = new ArrayList<>();
        
        for(JobRequest request : requests) {
            EmployeeToEvaluateDto employeeDto = new EmployeeToEvaluateDto();
            
            List<String> features = new ArrayList<>();
            for(JobFeature feature : request.getUser().getJobFeatures()) {
                features.add(feature.getName());
            }
            
            employeeDto.setDocument(request.getUser().getPersonalId());
            employeeDto.setFeatures(features);
            employees.add(employeeDto);
        }
        
        result.setEmployees(employees);
        
        return result;
    }
}
