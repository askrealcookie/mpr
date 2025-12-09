package org.example.export;

import org.example.model.Employee;
import org.example.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ExportService {

    private final EmployeeService employeeService;
    private final EmployeeDataFormatter formatter;

    public ExportService(EmployeeService employeeService, EmployeeDataFormatter formatter) {
        this.employeeService = employeeService;
        this.formatter = formatter;
    }

    public ExportResult exportEmployees(ExportRequest request) {
        List<Employee> employees = employeeService.getAllEmployees();

        List<Employee> filtered = employees.stream()
                .filter(e -> request.getPositionsCodes() == null || request.getPositionsCodes().isEmpty()
                        || request.getPositionsCodes().contains(e.getPosition().name()))
                .collect(Collectors.toList());

        String content = formatter.formatEmployees(filtered, request.getFormat());
        return new ExportResult(content, request.getFormat(), LocalDateTime.now());
    }
}
