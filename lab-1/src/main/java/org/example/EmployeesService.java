package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeesService {
    private final Map<String, Employee> employees = new HashMap<>();

    public boolean add(Employee employee) {
        String email = employee.getEmail();

        if(employees.containsKey(email)) {
            System.out.println("istnieje juz pracownik z takim eamilem");
            return false;
        }

        employees.put(email, employee);
        return true;
    }

    public List<Employee> all() {
        return List.copyOf(employees.values());
    }

    public List<Employee> allInCompany(String company) {
        List<Employee> employees = new ArrayList<>();

        for(Employee employee : all()) {
            if(employee.getCompanyName().equals(company)) {
                employees.add(employee);
            }
        }

        return employees;
    }

    public Map<Position, List<Employee>> allGroupByPosition() {
        Map<Position, List<Employee>> map = new HashMap<>();

        for(Employee employee : all()) {
            if(map.containsKey(employee.getPosition())) {
                map.get(employee.getPosition()).add(employee);
            } else {
                map.put(employee.getPosition(), new ArrayList<>());
                map.get(employee.getPosition()).add(employee);
            }
        }

        return map;
    }

    public Map<Position, Integer> countEmployeesByPositions() {
        Map<Position, Integer> map = new HashMap<>();
        for(Employee employee : all()) {
            if(map.containsKey(employee.getPosition())) {
                map.replace(employee.getPosition(), map.get(employee.getPosition())+1);
            } else {
                map.put(employee.getPosition(), 1);
            }
        }

        return map;
    }

    public Employee findEmployeeWithBestSalary() {
        if (employees.isEmpty()) {
            return null;
        }
        Employee best = (Employee) employees.values().toArray()[0];
        for (Employee p : employees.values()) {
            if (p.getPosition().getSalary() > best.getPosition().getSalary()) {
                best = p;
            }
        }
        return best;
    }

    public List<Employee> employeesSortedByLastName() {
        List<Employee> sorted = new ArrayList<>(employees.values());
        sorted.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee p1, Employee p2) {
                return p1.getLastName().compareToIgnoreCase(p2.getLastName());
            }
        });
        return sorted;
    }

    public double calculateAverageSalary() {
        if (employees.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Employee employee : employees.values()) {
            sum += employee.getPosition().getSalary();
        }

        return sum / employees.size();
    }
}
