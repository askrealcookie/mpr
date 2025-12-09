package org.example.service;

import org.example.model.Employee;
import org.example.model.CompanyStatistics;
import org.example.model.Position;

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
            double averageSalary = count == 0 ? 0 : totalSalary / count;

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

    public List<Employee> findByCompany(String companyName) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getCompanyName().equals(companyName)) {
                result.add(employee);
            }
        }
        return result;
    }

    public List<Employee> sortByLastName() {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort(Comparator
                .comparing(Employee::getLastName)
                .thenComparing(Employee::getFirstName));
        return copy;
    }

    public Map<Position, List<Employee>> groupByPosition() {
        Map<Position, List<Employee>> result = new HashMap<>();
        for (Employee employee : employees) {
            Position position = employee.getPosition();
            List<Employee> list = result.get(position);
            if (list == null) {
                list = new ArrayList<>();
                result.put(position, list);
            }
            list.add(employee);
        }
        return result;
    }

    public Map<Position, Long> countByPosition() {
        Map<Position, Long> result = new HashMap<>();
        for (Employee employee : employees) {
            Position position = employee.getPosition();
            Long current = result.get(position);
            if (current == null) {
                result.put(position, 1L);
            } else {
                result.put(position, current + 1);
            }
        }
        return result;
    }

    public double getAverageSalary() {
        if (employees.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Employee e : employees) {
            sum += e.getSalary();
        }
        return sum / employees.size();
    }

    public Employee getTopEarner() {
        if (employees.isEmpty()) {
            return null;
        }
        Employee top = employees.get(0);
        for (Employee e : employees) {
            if (e.getSalary() > top.getSalary()) {
                top = e;
            }
        }
        return top;
    }
}