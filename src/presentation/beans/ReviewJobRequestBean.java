
package presentation.beans;

import data.entities.JobRequest;
import external.services.soap.clients.verify.ResultDto;
import external.services.soap.clients.verify.TestResultDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ReviewJobRequestBean implements Serializable{
    private JobRequest jobRequest;
    private List<TestResultDto> physicalExamResults;
    private List<TestResultDto> skillsExamResults;

    public JobRequest getJobRequest() {
        return jobRequest;
    }

    public void setJobRequest(JobRequest jobRequest) {
        this.jobRequest = jobRequest;
    }

    public List<TestResultDto> getPhysicalExamResults() {
        return physicalExamResults;
    }

    public void setPhysicalExamResults(List<TestResultDto> physicalExamResults) {
        this.physicalExamResults = physicalExamResults;
    }

    public List<TestResultDto> getSkillsExamResults() {
        return skillsExamResults;
    }

    public void setSkillsExamResults(List<TestResultDto> skillsExamResults) {
        this.skillsExamResults = skillsExamResults;
    }
    
}
