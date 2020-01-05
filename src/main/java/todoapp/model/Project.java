package main.java.todoapp.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private long id;
    private String name;
    private User owner;
    private List<User> members = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public Project() {

    }

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        members.add(owner);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        StringBuilder projectText = new StringBuilder("Project \"" + name + "\" with owner " + owner.getUsername() + ".\nMembers:");
        for (User member : members) {
            projectText.append("\n\t").append(member);
        }

        if (tasks.isEmpty()) {
	        return projectText.toString();
        }
	
	    projectText.append("\nAnd is made out of these main tasks:");

        for (Task task : tasks) {
            projectText.append("\n\t").append(task);
        }
	    return projectText.toString();
    }

    @Override
    public boolean equals(Object project) {
        if (project == null) {
            return false;
        } else return ((Project) project).getName().equals(name) && ((Project) project).getOwner().equals(owner);
    }

}
