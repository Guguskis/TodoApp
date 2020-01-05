package main.java.todoapp.controller.mainscreen.task;

public class UpdateTaskDto {
    private long id;
    private String title;
    private boolean completed;
    private String updatorUsername;

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getUpdatorUsername() {
        return updatorUsername;
    }

    public void setUpdatorUsername(String updatorUsername) {
        this.updatorUsername = updatorUsername;
    }
}
