package org.example.export;

import org.example.model.Employee;

import java.util.List;

public interface EmployeeDataFormatter {

    String formatEmployees(List<Employee> employees, ExportFormat format);
}

