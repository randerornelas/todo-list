package todoListBackend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_time")
    private String startTime;

    private LocalDate deadline;

    public TaskModel() {

    }

    public TaskModel(String taskName, LocalDate startDate, String startTime, LocalDate deadline) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.deadline = deadline;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
