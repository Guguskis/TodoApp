package main.java.todoapp.model;

import main.java.todoapp.exceptions.DuplicateException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task implements Serializable {

    private List<Task> tasks;
    private boolean completed;
    private String name;
    private User createdBy;
    private LocalDateTime createdDate;
    private User completedBy;
    private LocalDateTime completedDate;

    public Task(String name, User creator) {
        this.tasks = new ArrayList();
        this.completed = false;
        this.name = name;
        this.createdBy = creator;
        this.createdDate = LocalDateTime.now();
        this.completedBy = null;
        this.completedDate = null;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(User user) {
        completed = true;
        completedBy = user;
        completedDate = LocalDateTime.now();
    }

    public void setIncompleted() {
        completed = false;
        completedBy = null;
        completedDate = null;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void addTask(User creator, Task task) throws DuplicateException {
        var duplicates = tasks.stream()
                .filter(t -> t.getName().equals(task.getName()))
                .collect(Collectors.toList());

        if (!duplicates.isEmpty()) {
            throw new DuplicateException("Task \"" + task.getName() + "\" already exists.");
        }

        task.setCreatedBy(creator);
        tasks.add(task);
    }

    public String getDurationAfterCompletion() {
        if (!completed) {
            return "not completed";
        }
        var secondsPassed = ChronoUnit.SECONDS.between(completedDate, LocalDateTime.now());
        var minutesPassed = ChronoUnit.MINUTES.between(completedDate, LocalDateTime.now());
        var hoursPassed = ChronoUnit.HOURS.between(completedDate, LocalDateTime.now());
        var daysPassed = ChronoUnit.DAYS.between(completedDate, LocalDateTime.now());
        var weeksPassed = ChronoUnit.WEEKS.between(completedDate, LocalDateTime.now());
        var monthsPassed = ChronoUnit.MONTHS.between(completedDate, LocalDateTime.now());
        var yearsPassed = ChronoUnit.YEARS.between(completedDate, LocalDateTime.now());

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

    @Override
    public String toString() {
        String taskText = "Task \"" + name + "\" was created by " + createdBy.getUsername() + " on " + createdDate + ". ";

        if (completed) {
            taskText += "It was completed by " + completedBy.getUsername() + " " + getDurationAfterCompletion() + " ago";
        } else {
            taskText += "It is not completed.";
        }

        return taskText;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void deleteTask(Task subtaskToRemove) {
        tasks.removeIf(task -> task.equals(subtaskToRemove));
    }

}
