package data.entities;

import java.sql.Date;

public class UsersJobs {
    private int id;
    private Date startTime;
    private Date endTime;
    private int userId;
    private int jobId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersJobs)) return false;

        UsersJobs usersJobs = (UsersJobs) o;

        if (getId() != usersJobs.getId()) return false;
        if (getUserId() != usersJobs.getUserId()) return false;
        if (getJobId() != usersJobs.getJobId()) return false;
        if (getStartTime() != null ? !getStartTime().equals(usersJobs.getStartTime()) : usersJobs.getStartTime() != null)
            return false;
        return getEndTime() != null ? getEndTime().equals(usersJobs.getEndTime()) : usersJobs.getEndTime() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + getUserId();
        result = 31 * result + getJobId();
        return result;
    }
}
