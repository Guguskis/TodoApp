package main.java.todoapp.communication;

import main.java.todoapp.exceptions.HttpRequestFailedException;
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
public class UserConnection {
    private final String BASE_URL = "http://localhost:8082/api/user/";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public boolean verify(String username, String password) throws IOException, InterruptedException {
        JSONObject userJson = getUserJson(new User(username, password));
        HttpRequest request = createPostRequest(userJson, "/verify");
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

    public User getUserInfo(String username) throws Throwable {
        HttpRequest request = createGetRequest(username);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            throw new HttpRequestFailedException("Failed to get user info");
        }

        return getUser(response.body());
    }

    public void updateUserInformation(User user) throws IOException, InterruptedException, HttpRequestFailedException {
        JSONObject json;
        String endpoint;
        if (user instanceof Person) {
            json = getPersonJson((Person) user);
            endpoint = "person";
        } else {
            json = getCompanyJson((Company) user);
            endpoint = "company";
        }

        HttpRequest request = createPutRequest(json, endpoint);
        HttpResponse<String> response = send(request);

        if (response.statusCode() != 200) {
            throw new HttpRequestFailedException("Failed to update user information.");
        }
    }

    private HttpRequest createPutRequest(JSONObject body, String endpoint) {
        return newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
    }

    private User getUser(String responseBody) throws Exception {
        JSONObject json = new JSONObject(responseBody);
        if (isCompany(json)) {
            return getCompany(json);
        } else if (isPerson(json)) {
            return getPerson(json);
        } else {
            throw new Exception("Server and client types do not match.");
        }
    }

    private Person getPerson(JSONObject json) throws JSONException {
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

    private Company getCompany(JSONObject json) throws JSONException {
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

    private HttpRequest createGetRequest(String endpoint) {
        return newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .build();
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

    private JSONObject getCompanyJson(Company company) {
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
