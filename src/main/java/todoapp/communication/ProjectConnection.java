package main.java.todoapp.communication;

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
        List<SimplifiedProjectDto> projects = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            SimplifiedProjectDto project = new SimplifiedProjectDto();
            project.setId(json.getLong("id"));
            project.setName(json.getString("name"));
            project.setOwner(json.getString("owner"));
            project.setMemberCount(json.getInt("memberCount"));
            projects.add(project);
        }

        return projects;
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
}
