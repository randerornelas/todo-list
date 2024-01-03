package model;

public class TaskModel {

    private int id;

    private String taskName;
    private String startDate;
    private String startTime;
    private String deadline;
    private String deleteLink;

    public TaskModel(String taskName, String startDate, String startTime, String deadline, String deleteLink) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.deadline = deadline;
        this.deleteLink = deleteLink;
    }

    public TaskModel(int id, String taskName, String startDate, String startTime, String deadline, String deleteLink) {
        this(taskName, startDate, startTime, deadline, deleteLink);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDeleteLink() {
        return deleteLink;
    }

    public void setDeleteLink(String deleteLink) {
        this.deleteLink = deleteLink;
    }
}
