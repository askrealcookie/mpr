package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.model.Employee;
import org.example.model.Position;
import org.example.exception.ApiException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private final HttpClient httpClient;
    private final Gson gson;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Employee> fetchEmployeesFromApi() throws ApiException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ApiException("blad APi: " + response.statusCode());
            }

            JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
            List<Employee> employees = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject userJson = jsonArray.get(i).getAsJsonObject();
                employees.add(parseJsonToEmployee(userJson));
            }

            return employees;
        } catch (IOException | InterruptedException e) {
            throw new ApiException("blad", e);
        }
    }

    private Employee parseJsonToEmployee(JsonObject json) {
        String fullName = json.get("name").getAsString();
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";
        String email = json.get("email").getAsString();
        String companyName = json.getAsJsonObject("company").get("name").getAsString();

        return new Employee(
            firstName,
            lastName,
            email,
            companyName,
            Position.Programista,
            Position.Programista.getSalary()
        );
    }
}