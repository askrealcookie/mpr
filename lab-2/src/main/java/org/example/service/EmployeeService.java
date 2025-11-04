package org.example.service;

import org.example.model.Employee;
import org.example.model.CompanyStatistics;

import java.util.*;

public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> validateSalaryConsistency() {
        List<Employee> inconsistentEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() < employee.getPosition().getSalary()) {
                inconsistentEmployees.add(employee);
            }
        }
        return inconsistentEmployees;
    }

    public Map<String, CompanyStatistics> getCompanyStatistics() {
        Map<String, List<Employee>> companiesMap = new HashMap<>();

        for (Employee employee : employees) {
            String company = employee.getCompanyName();
            List<Employee> list = companiesMap.computeIfAbsent(company, k -> new ArrayList<>());
            list.add(employee);
        }


        Map<String, CompanyStatistics> statistics = new HashMap<>();
        for (Map.Entry<String, List<Employee>> entry : companiesMap.entrySet()) {
            List<Employee> companyEmployees = entry.getValue();
            String companyName = entry.getKey();

            int count = companyEmployees.size();

            double totalSalary = 0;
            for (Employee employee : companyEmployees) {
                totalSalary += employee.getSalary();
            }
            double averageSalary = totalSalary / count;

            Employee topEarner = companyEmployees.get(0);
            for (Employee employee : companyEmployees) {
                if (employee.getSalary() > topEarner.getSalary()) {
                    topEarner = employee;
                }
            }
            
            statistics.put(companyName, new CompanyStatistics(
                count,
                averageSalary,
                topEarner.getFullName()
            ));
        }
        
        return statistics;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}