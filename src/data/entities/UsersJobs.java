package data.entities;

import java.util.Date;

public class UsersJobs {
    private int id;
    private Date startTime;
    private Date endTime;
    private User user;
    private Job job;


    public int getId() {
        return id;
    }

    private void setId(int id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersJobs)) return false;

        UsersJobs usersJobs = (UsersJobs) o;

        if (getId() != usersJobs.getId()) return false;
        if (getUser() != usersJobs.getUser()) return false;
        if (getJob() != usersJobs.getJob()) return false;
        if (getStartTime() != null ? !getStartTime().equals(usersJobs.getStartTime()) : usersJobs.getStartTime() != null)
            return false;
        return getEndTime() != null ? getEndTime().equals(usersJobs.getEndTime()) : usersJobs.getEndTime() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getUser()!= null ? getUser().hashCode() : 0);
        result = 31 * result + (getJob()!= null ? getJob().hashCode() : 0);
        return result;
    }
}
