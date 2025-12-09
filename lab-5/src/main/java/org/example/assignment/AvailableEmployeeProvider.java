package org.example.assignment;

import org.example.model.Employee;

import java.util.List;

public interface AvailableEmployeeProvider {

    List<Employee> findAvailableEmployees(int estimatedHours);
}

