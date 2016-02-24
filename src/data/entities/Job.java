package data.entities;

import java.util.List;
import data.entities.JobFeature;

public class Job {
    private int id;
    private String name;
    private String description;
    private Integer salary;
    private List<JobFeature> jobFeatures;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public List<JobFeature> getJobFeatures() {
        return jobFeatures;
    }

    public void setJobFeatures(List<JobFeature> jobFeatures) {
        this.jobFeatures = jobFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;

        Job job = (Job) o;

        if (getId() != job.getId()) return false;
        if (getName() != null ? !getName().equals(job.getName()) : job.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(job.getDescription()) : job.getDescription() != null)
            return false;
        if (getSalary() != null ? !getSalary().equals(job.getSalary()) : job.getSalary() != null) return false;
        return getJobFeatures() != null ? getJobFeatures().equals(job.getJobFeatures()) : job.getJobFeatures() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getSalary() != null ? getSalary().hashCode() : 0);
        result = 31 * result + (getJobFeatures() != null ? getJobFeatures().hashCode() : 0);
        return result;
    }
}
