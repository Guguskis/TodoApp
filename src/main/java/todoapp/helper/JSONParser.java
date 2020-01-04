package main.java.todoapp.helper;

import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public JSONObject parse(User user) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", user.getUsername());
            json.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public JSONObject parse(SimplifiedProjectDto project) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("projectName", project.getName());
        json.put("ownerUsername", project.getOwner());
        json.put("members", project.getMembers());
        return json;
    }

    public List<SimplifiedProjectDto> simplifiedProjectsDto(JSONArray jsonArray) throws JSONException {
        List<SimplifiedProjectDto> projects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            projects.add(simplifiedProjectDto(json));
        }
        return projects;
    }

    private SimplifiedProjectDto simplifiedProjectDto(JSONObject json) throws JSONException {
        SimplifiedProjectDto project = new SimplifiedProjectDto();
        project.setId(json.getLong("id"));
        project.setName(json.getString("name"));
        project.setOwner(json.getString("owner"));
        List<String> members = getMembers(json);
        project.setMembers(members);
        return project;
    }

    private List<String> getMembers(JSONObject json) throws JSONException {
        JSONArray jsonMembers = json.getJSONArray("members");
        List<String> members = new ArrayList<>();
        for (int i = 0; i < jsonMembers.length(); i++) {
            members.add(jsonMembers.getString(i));
        }
        String owner = json.getString("owner");
        members.remove(owner);

        return members;
    }

    public User user(JSONObject json) throws Exception {
        if (isCompany(json)) {
            return company(json);
        } else if (isPerson(json)) {
            return person(json);
        } else {
            throw new Exception("Server and client types do not match.");
        }
    }

    private Person person(JSONObject json) throws JSONException {
        Person person = new Person();
        person.setId(json.getInt("id"));
        person.setUsername(json.getString("username"));
        person.setPassword(json.getString("password"));
        person.setFirstName(json.getString("firstName"));
        person.setLastName(json.getString("lastName"));
        person.setPhone(json.getString("phone"));
        person.setEmail(json.getString("email"));
        return person;
    }

    private Company company(JSONObject json) throws JSONException {
        Company company = new Company();
        company.setId(json.getInt("id"));
        company.setUsername(json.getString("username"));
        company.setPassword(json.getString("password"));
        company.setName(json.getString("name"));
        company.setContactPersonPhone(json.getString("contactPersonPhone"));
        return company;
    }

    private boolean isPerson(JSONObject json) {
        return json.has("firstName") && json.has("lastName") && json.has("phone") && json.has("email");
    }

    private boolean isCompany(JSONObject json) {
        return json.has("name") && json.has("contactPersonPhone");
    }

    public JSONObject getPersonJson(Person person) {
        JSONObject personJson = new JSONObject();
        try {
            personJson.put("id", person.getId());
            personJson.put("firstName", person.getFirstName());
            personJson.put("lastName", person.getLastName());
            personJson.put("phone", person.getPhone());
            personJson.put("email", person.getEmail());
            personJson.put("username", person.getUsername());
            personJson.put("password", person.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return personJson;
    }

    public JSONObject getCompanyJson(Company company) {
        JSONObject companyJson = null;
        try {
            companyJson = new JSONObject();
            companyJson.put("id", company.getId());
            companyJson.put("name", company.getName());
            companyJson.put("contactPersonPhone", company.getContactPersonPhone());
            companyJson.put("username", company.getUsername());
            companyJson.put("password", company.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return companyJson;
    }
}
