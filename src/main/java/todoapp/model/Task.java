package main.java.todoapp.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private long id;
    private String title;
    private boolean completed;

    private String createdBy;
    private LocalDateTime createdDate;
    private String completedBy;
    private LocalDateTime completedDate;

    private List<Task> tasks = new ArrayList<>();


    public Task(String title, String creator) {
        this.title = title;
        this.createdBy = creator;
        this.createdDate = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(String completedBy) {
        this.completed = true;
        this.completedBy = completedBy;
        this.setCompletedDate(LocalDateTime.now());
    }

    public void setIncomplete() {
        completed = false;
        completedBy = "";
        completedDate = null;
    }

    public String getDurationAfterCompletion() {
        if (!completed) {
            return "not completed";
        }

        long secondsPassed = ChronoUnit.SECONDS.between(completedDate, LocalDateTime.now());
        long minutesPassed = ChronoUnit.MINUTES.between(completedDate, LocalDateTime.now());
        long hoursPassed = ChronoUnit.HOURS.between(completedDate, LocalDateTime.now());
        long daysPassed = ChronoUnit.DAYS.between(completedDate, LocalDateTime.now());
        long weeksPassed = ChronoUnit.WEEKS.between(completedDate, LocalDateTime.now());
        long monthsPassed = ChronoUnit.MONTHS.between(completedDate, LocalDateTime.now());
        long yearsPassed = ChronoUnit.YEARS.between(completedDate, LocalDateTime.now());

        if (secondsPassed < 60) {
            return "less than a minute";
        } else if (minutesPassed < 60) {
            return minutesPassed + " minutes";
        } else if (hoursPassed < 24) {
            return hoursPassed + " hours";
        } else if (daysPassed < 10) {
            return daysPassed + " days";
        } else if (weeksPassed < 9) {
            return weeksPassed + " weeks";
        } else if (monthsPassed < 12) {
            return monthsPassed + " months";
        } else {
            return yearsPassed + " years";
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        String string = title + " created by " + createdBy + ". ";

        if (isCompleted()) {
            string += "Completed by " + completedBy + " " + getDurationAfterCompletion() + " ago.";
        } else {
            string += "It was not completed.";
        }

        return string;
    }
}
