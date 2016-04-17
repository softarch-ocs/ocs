package external.services.soap;

import data.entities.JobFeature;
import data.entities.JobRequest;
import data.entities.User;
import external.dto.EmployeeToEvaluateDto;
import external.dto.EmployeesToEvaluateDto;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import services.UserService;
import services.jobs.JobRequestService;


@WebService(serviceName = "GetEmployeesToEvaluate", 
        targetNamespace = "http://192.168.0.30/ocs/soap/")
public class GetEmployeesToEvaluate {
    private JobRequestService jobRequestService;
    private UserService userService;
    
    public GetEmployeesToEvaluate(UserService userService, 
            JobRequestService jobRequestService) {
        this.userService = userService;
        this.jobRequestService = jobRequestService;
    }
    
    public GetEmployeesToEvaluate() {
        this(new UserService(), new JobRequestService());
    }

    @WebMethod(operationName = "get")
    public EmployeesToEvaluateDto get(@WebParam(name = "userName") String userName, 
            @WebParam(name = "password") String password) throws UnauthorizedException {
        User user = userService.getUserByEmailAndPassword(userName, password);
        
        if (user == null || !user.hasRole(User.Role.WEB_SERVICE)) {
            throw new UnauthorizedException(
                    "You are not authorized to access this resource");
        }
        
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
