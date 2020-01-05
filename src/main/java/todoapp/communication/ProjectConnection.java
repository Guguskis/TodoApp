package main.java.todoapp.communication;

import main.java.todoapp.dto.SimplifiedProjectDto;
import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.helper.JSONParser;
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

public class ProjectConnection {
    private final String BASE_URL = "http://localhost:8082/api/project/";

    private final HttpClient httpClient;
    private final JSONParser parser;

    public ProjectConnection(JSONParser parser) {
        this.parser = parser;
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public List<SimplifiedProjectDto> sendGet(String username) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createGetRequest(username);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            parseAndThrowHttpRequestFailedException(response);
        }

        return parser.simplifiedProjectsDto(new JSONArray(response.body()));
    }

    public void sendPost(String owner, List<String> usernames, String name) throws JSONException, IOException, InterruptedException, HttpRequestFailedException {
        SimplifiedProjectDto project = new SimplifiedProjectDto(name, owner, usernames);
        HttpRequest request = createPostRequest(parser.parse(project), "");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            parseAndThrowHttpRequestFailedException(response);
        }
    }

    public void sendDelete(long id) throws IOException, InterruptedException {
        HttpRequest request = createDeleteRequest(id);
        send(request);
    }

    public void sendPut(SimplifiedProjectDto project) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        HttpRequest request = createPutRequest(new JSONObject(project));
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            parseAndThrowHttpRequestFailedException(response);
            return;
        }
    }

    private HttpRequest createGetRequest(String endpoint) {
        return newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest createPostRequest(JSONObject body, String endpoint) {
        return newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest createDeleteRequest(long id) {
        return newBuilder()
                .DELETE()
                .uri(URI.create(BASE_URL + id))
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest createPutRequest(JSONObject body) {
        return newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .build();
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
