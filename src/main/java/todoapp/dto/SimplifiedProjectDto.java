package main.java.todoapp.dto;

import java.util.List;

public class SimplifiedProjectDto {
    private long id;
    private String name;
    private String owner;
    private List<String> members;

    public SimplifiedProjectDto() {

    }

    public SimplifiedProjectDto(String name, String owner, List<String> members) {
        this.name = name;
        this.owner = owner;
        this.members = members;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public int getMemberCount() {
        int owner = 1;
        return members.size() + owner;
    }
}
