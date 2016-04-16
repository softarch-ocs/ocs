package external.dto;

import java.io.Serializable;
import java.util.List;


public class EmployeesToEvaluateDto implements Serializable {
    private List<EmployeeToEvaluateDto> employees;

    public List<EmployeeToEvaluateDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeToEvaluateDto> employees) {
        this.employees = employees;
    }
    
}