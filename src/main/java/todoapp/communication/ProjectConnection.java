package main.java.todoapp.communication;

import javafx.scene.control.Alert;
import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static java.net.http.HttpRequest.newBuilder;

public class ProjectConnection {
    private final String BASE_URL = "http://localhost:8082/api/project/";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public List<SimplifiedProjectDto> getProjects(String username) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createGetRequest(username);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            throw new HttpRequestFailedException("Failed to get project list");
        }

        JSONArray jsonArray = new JSONArray(response.body());
        List<SimplifiedProjectDto> projects = getSimplifiedProjectsDto(jsonArray);
        return projects;
    }

    private List<SimplifiedProjectDto> getSimplifiedProjectsDto(JSONArray jsonArray) throws JSONException {
        List<SimplifiedProjectDto> projects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            projects.add(parseSimplifiedProjectDto(json));
        }
        return projects;
    }

    private SimplifiedProjectDto parseSimplifiedProjectDto(JSONObject json) throws JSONException {
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

    private HttpRequest createGetRequest(String endpoint) {
        return newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void create(String owner, List<String> usernames, String name) throws JSONException, IOException, InterruptedException {
        HttpRequest request = createPostRequest(getProjectJson(owner, usernames, name), "");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            JSONObject responseBodyJson = new JSONObject(response.body());
            String message = responseBodyJson.getString("message");
            //Todo remove this
            triggerAlert(message);
        }
    }

    private JSONObject getProjectJson(String owner, List<String> usernames, String name) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("projectName", name);
        json.put("ownerUsername", owner);
        json.put("members", usernames);
        return json;
    }

    private HttpRequest createPostRequest(JSONObject body, String endpoint) {
        return newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    private void triggerAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void delete(long id) throws IOException, InterruptedException {
        HttpRequest request = createDeleteRequest(id);
        send(request);
    }

    private HttpRequest createDeleteRequest(long id) {
        return newBuilder()
                .DELETE()
                .uri(URI.create(BASE_URL + id))
                .header("Content-Type", "application/json")
                .build();
    }

    public void update(SimplifiedProjectDto project) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        JSONObject json = new JSONObject(project);
        HttpRequest request = createPutRequest(json);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
//            JSONObject responseBodyJson = new JSONObject(response.body());
//            String message = responseBodyJson.getString("message");
//            throw new HttpRequestFailedException(message);
            throw new HttpRequestFailedException("Project could not be updated.");
            //Todo show a good error message
        }
    }

    private HttpRequest createPutRequest(JSONObject body) {
        return newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .build();
    }
}
