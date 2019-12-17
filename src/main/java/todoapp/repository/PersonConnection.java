package main.java.todoapp.repository;

import main.java.todoapp.exceptions.RegistrationFailedException;
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

/*
 * example
 * https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 * */
public class PersonConnection {
    private final String BASE_URL = "http://localhost:8082/api/user/";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public boolean login(User user) throws IOException, InterruptedException {
        HttpRequest request = createPostRequest(getUserJson(user), "/verify");
        HttpResponse<String> response = send(request);
        return response.body().equals("true");
    }

    public void register(Company company) throws IOException, InterruptedException, RegistrationFailedException {
        HttpRequest request = createPostRequest(getCompanyJson(company), "/company");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            throw new RegistrationFailedException(response.body());
        }
    }

    public void register(Person person) throws RegistrationFailedException, IOException, InterruptedException {
        HttpRequest request = createPostRequest(getPersonJson(person), "/person");
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 201) {
            throw new RegistrationFailedException(response.body());
        }
    }

    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, BodyHandlers.ofString());
    }

    private HttpRequest createPostRequest(JSONObject body, String endpoint) {
        return newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    private JSONObject getUserJson(User user) {
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("username", user.getUsername());
            userJson.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson;
    }

    private JSONObject getPersonJson(Person person) {
        JSONObject personJson = new JSONObject();
        try {
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

    private JSONObject getCompanyJson(Company company) {
        JSONObject companyJson = null;
        try {
            companyJson = new JSONObject();
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
