package main.java.todoapp.model;

import main.java.todoapp.exceptions.DuplicateException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Project {

    private String name;
    private User owner;
    private List<User> members;
    private List<Task> tasks;

    public Project(String name) {
        this.name = name;
        this.owner = null;
        this.members = new ArrayList();
        this.tasks = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        members.add(owner);
    }

    public List<User> getMembers() {
        return members;
    }
	
	public void addMember(User whoIsAdding, User userToAdd) throws Exception {
        if (members.contains(userToAdd)) {
            throw new DuplicateException("User " + userToAdd + " already exists.");

        } else if (this.owner != whoIsAdding) {
            throw new Exception("User " + whoIsAdding.getUsername() + " cannot add another member, because he is not project owner.");
        } else {
            members.add(userToAdd);
            userToAdd.assignProject(this);
        }
    }

    public void removeMember(User user) {
        if (user.equals(owner)) {
            return;
        }
        members.remove(user);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) throws DuplicateException {
        var duplicate = tasks.stream()
                .filter(t -> t.getName().equals(task.getName()))
                .collect(Collectors.toList());
        if (!duplicate.isEmpty()) {
            throw new DuplicateException("Task \"" + task.getName() + "\" already exists.");
        }

        tasks.add(task);
    }

    public void removeTask(Task taskToRemove) {
        tasks.removeIf(task -> task.equals(taskToRemove));
    }

    @Override
    public String toString() {
	    StringBuilder projectText = new StringBuilder("Project \"" + name + "\" with owner " + owner.getUsername() + ".\nMembers:");
        for (var member : members) {
	        projectText.append("\n\t").append(member);
        }

        if (tasks.isEmpty()) {
	        return projectText.toString();
        }
	
	    projectText.append("\nAnd is made out of these main tasks:");

        for (var task : tasks) {
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

    public boolean isOwner(User user) {
        if (owner != null) {
            return owner.equals(user);
        }
        return false;
    }

}
