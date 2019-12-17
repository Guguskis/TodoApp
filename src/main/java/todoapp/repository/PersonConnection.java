package main.java.todoapp.repository;

import main.java.todoapp.exceptions.RegistrationFailedException;
import main.java.todoapp.model.Company;
import main.java.todoapp.model.Person;
import main.java.todoapp.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers;

/*
* example
* https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
* */
public class PersonConnection {
    private final String BASE_URL = "http://localhost:8082/api/user/";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) throws Exception {

        PersonConnection connection = new PersonConnection();
        Company company = new Company("MatasTM", "555",  "Matukuliszzz", "matas");
        connection.register(company);
        System.out.println("Success??");
    }

    public boolean login(User user) throws IOException, InterruptedException, JSONException {
        JSONObject userJson = new JSONObject();
        userJson.put("username", user.getUsername());
        userJson.put("password", user.getPassword());

        HttpRequest request = createPostRequest(userJson, "/verify");
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body().equals("true");
    }

    private HttpRequest createPostRequest(JSONObject body, String endpoint) {
        return newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    public void register(Person person) throws JSONException, RegistrationFailedException, IOException, InterruptedException {
        JSONObject personJson = new JSONObject();
        personJson.put("firstName", person.getFirstName());
        personJson.put("lastName", person.getLastName());
        personJson.put("phone", person.getPhone());
        personJson.put("email", person.getEmail());
        personJson.put("username", person.getUsername());
        personJson.put("password", person.getPassword());

        HttpRequest request = createPostRequest(personJson, "/person");
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new RegistrationFailedException(response.body());
        }
    }

    public void register(Company company) throws JSONException, IOException, InterruptedException, RegistrationFailedException {
        JSONObject companyJson = new JSONObject();
        companyJson.put("name", company.getName());
        companyJson.put("contactPersonPhone", company.getContactPersonPhone());
        companyJson.put("username", company.getUsername());
        companyJson.put("password", company.getPassword());

        HttpRequest request = createPostRequest(companyJson, "/company");
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new RegistrationFailedException(response.body());
        }
    }
}
