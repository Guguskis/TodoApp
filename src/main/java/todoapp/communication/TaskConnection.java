package main.java.todoapp.communication;

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
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private JSONParser parser;

    public TaskConnection(JSONParser parser) {
        this.parser = parser;
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

    public List<Task> get(long projectId) throws IOException, InterruptedException, JSONException, HttpRequestFailedException {
        HttpRequest request = createGetRequest("" + projectId);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            JSONObject responseBodyJson = new JSONObject(response.body());
            String message = responseBodyJson.getString("message");
            throw new HttpRequestFailedException(message);
        }

        return parser.getTasks(new JSONArray(response.body()));
    }
}
