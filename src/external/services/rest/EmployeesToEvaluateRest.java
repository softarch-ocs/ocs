
package external.services.rest;

import data.entities.JobFeature;
import data.entities.JobRequest;
import data.entities.User;
import external.dto.EmployeeToEvaluateDto;
import external.dto.EmployeesToEvaluateDto;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.UserService;
import services.jobs.JobRequestService;


@Path("rest/employeesToEvaluate")
public class EmployeesToEvaluateRest {

    @Context
    private UriInfo context;
    
    private JobRequestService jobRequestService;
    private UserService userService;

    public EmployeesToEvaluateRest(JobRequestService jobRequestService, UserService userService) {
        this.jobRequestService = jobRequestService;
        this.userService = userService;
        
    }

    public EmployeesToEvaluateRest() {
        this(new JobRequestService(), new UserService());
    }

    /**
     * Retrieves representation of an instance of external.services.rest.EmployeesToEvaluateRest
     * @param userName
     * @param password
     * @return an instance of external.dto.EmployeesToEvaluateDto
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public EmployeesToEvaluateDto getJson(
            @HeaderParam("X-Username") String userName,
            @HeaderParam("X-Password") String password
            ) {
               
        /*
        User user = userService.getUserByEmailAndPassword(userName, password);
        
        if (user == null || !user.hasRole(User.Role.WEB_SERVICE)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        */
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

