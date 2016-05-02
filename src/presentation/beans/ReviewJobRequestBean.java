
package presentation.beans;

import data.entities.JobRequest;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ReviewJobRequestBean implements Serializable{
    private JobRequest jobRequest;
    private ArrayList<String> physicalExamResults;
    private ArrayList<String> skillsExamResults;

    public JobRequest getJobRequest() {
        return jobRequest;
    }

    public void setJobRequest(JobRequest jobRequest) {
        this.jobRequest = jobRequest;
    }

    public ArrayList<String> getPhysicalExamResults() {
        return physicalExamResults;
    }

    public void setPhysicalExamResults(ArrayList<String> physicalExamResults) {
        this.physicalExamResults = physicalExamResults;
    }

    public ArrayList<String> getSkillsExamResults() {
        return skillsExamResults;
    }

    public void setSkillsExamResults(ArrayList<String> skillsExamResults) {
        this.skillsExamResults = skillsExamResults;
    }
    
}
