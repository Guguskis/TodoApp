package main.java.todoapp.communication;

import main.java.todoapp.controller.mainscreen.task.UpdateTaskDto;
import main.java.todoapp.dto.CreateTaskDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.helper.JSONParser;
import main.java.todoapp.model.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.net.http.HttpRequest.newBuilder;

public class TaskConnection {
    private final String BASE_URL = "http://localhost:8082/api/task/";
    private final HttpClient httpClient;

    private JSONParser parser;

    public TaskConnection(JSONParser parser) {
        this.parser = parser;
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public List<Task> sendGet(long projectId) throws IOException, InterruptedException, JSONException, HttpRequestFailedException {
        HttpRequest request = createGetRequest(projectId);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            parseAndThrowHttpRequestFailedException(response);
        }

        return parser.getTasks(new JSONArray(response.body()));
    }

    public void sendPostForProject(CreateTaskDto task) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createPostRequest(new JSONObject(task), "project");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            parseAndThrowHttpRequestFailedException(response);
        }
    }

    public void sendPostForTask(CreateTaskDto dto) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createPostRequest(new JSONObject(dto), "");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            parseAndThrowHttpRequestFailedException(response);
        }
    }

    public void sendDelete(long id) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createDeleteRequest("" + id);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            parseAndThrowHttpRequestFailedException(response);
        }
    }

    public void sendUpdate(UpdateTaskDto dto) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createPutRequest(new JSONObject(dto));
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            parseAndThrowHttpRequestFailedException(response);
        }
    }

    private HttpRequest createGetRequest(long projectId) {
        return getJSONBuilder()
                .GET()
                .uri(URI.create(BASE_URL + projectId))
                .build();
    }

    private HttpRequest createPostRequest(JSONObject body, String endpoint) {
        return getJSONBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .build();
    }

    private HttpRequest createDeleteRequest(String endpoint) {
        return getJSONBuilder()
                .DELETE()
                .uri(URI.create(BASE_URL + endpoint))
                .build();
    }

    private HttpRequest createPutRequest(JSONObject body) {
        return getJSONBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL))
                .build();
    }

    private HttpRequest.Builder getJSONBuilder() {
        return newBuilder()
                .header("Content-Type", "application/json");
    }

    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void parseAndThrowHttpRequestFailedException(HttpResponse<String> response) throws JSONException, HttpRequestFailedException {
        JSONObject responseBodyJson = new JSONObject(response.body());
        String message = responseBodyJson.getString("message");
        throw new HttpRequestFailedException(message);
    }
}
