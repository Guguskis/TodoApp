package main.java.todoapp.communication;

import main.java.todoapp.exceptions.HttpRequestFailedException;
import main.java.todoapp.exceptions.RegistrationFailedException;
import main.java.todoapp.helper.JSONParser;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers;

public class UserConnection {
    private final String BASE_URL = "http://localhost:8082/api/user/";
    private final HttpClient httpClient;
    private JSONParser parser;

    public UserConnection(JSONParser parser) {
        this.parser = parser;
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public boolean sendVerify(String username, String password) throws IOException, InterruptedException {
        JSONObject userJson = parser.parse(new User(username, password));
        HttpRequest request = createPostRequest(userJson, "verify");
        HttpResponse<String> response = send(request);
        return response.body().equals("true");
    }

    public User sendGet(String username) throws IOException, InterruptedException, JSONException, HttpRequestFailedException {
        HttpRequest request = createGetRequest(username);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            return parseAndThrowHttpRequestFailedException(response);
        }

        return parser.user(new JSONObject(response.body()));
    }

    public void sendPost(Company company) throws IOException, InterruptedException, RegistrationFailedException {
        HttpRequest request = createPostRequest(parser.getCompanyJson(company), "/company");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            throw new RegistrationFailedException(response.body());
        }
    }

    public void sendPost(Person person) throws RegistrationFailedException, IOException, InterruptedException {
        HttpRequest request = createPostRequest(parser.getPersonJson(person), "/person");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            throw new RegistrationFailedException(response.body());
        }
    }

    public void sendPut(User user) throws IOException, InterruptedException, HttpRequestFailedException, JSONException {
        JSONObject json;
        String endpoint;
        if (user instanceof Person) {
            json = parser.getPersonJson((Person) user);
            endpoint = "person";
        } else {
            json = parser.getCompanyJson((Company) user);
            endpoint = "company";
        }

        HttpRequest request = createPutRequest(json, endpoint);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            parseAndThrowHttpRequestFailedException(response);
        }
    }

    private HttpRequest createPutRequest(JSONObject body, String endpoint) {
        return getJSONRequest()
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .build();
    }

    private HttpRequest.Builder getJSONRequest() {
        return newBuilder()
                .header("Content-Type", "application/json");
    }

    private HttpRequest createGetRequest(String endpoint) {
        return getJSONRequest()
                .GET()
                .uri(URI.create(BASE_URL + endpoint))
                .build();
    }

    private HttpRequest createPostRequest(JSONObject body, String endpoint) {
        return getJSONRequest()
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .build();
    }

    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, BodyHandlers.ofString());
    }

    private User parseAndThrowHttpRequestFailedException(HttpResponse<String> response) throws JSONException, HttpRequestFailedException {
        JSONObject responseBodyJson = new JSONObject(response.body());
        String message = responseBodyJson.getString("message");
        throw new HttpRequestFailedException(message);
    }

}
