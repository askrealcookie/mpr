package org.example.service;

import org.example.exception.ApiException;
import org.example.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApiSerivce test")
class ApiServiceTest {

    @Test
    void shouldCallFetchEmployeesFromApiWithoutException() {
        // Arrange
        ApiService apiService = new ApiService();

        // Act & Assert
        try {
            List<Employee> employees = apiService.fetchEmployeesFromApi();
            assertNotNull(employees);
        } catch (ApiException e) {
            fail("Metoda fetchEmployeesFromApi nie powinna rzucać ApiException w teście: " + e.getMessage());
        }
    }
}

