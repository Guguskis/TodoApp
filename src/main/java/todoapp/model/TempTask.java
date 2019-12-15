package main.java.todoapp.model;

public class TempTask {
    private long id;
    private String name;
    private long projectId;
    private long taskId;
    private boolean completed;
    private long completedById;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getCompletedById() {
        return completedById;
    }

    public void setCompletedById(long completedById) {
        this.completedById = completedById;
    }

    @Override
    public String toString() {
        return "TempTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
