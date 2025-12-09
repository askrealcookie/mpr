package org.example;

import org.example.model.CompanyStatistics;
import org.example.model.Employee;
import org.example.model.ImportSummary;
import org.example.service.ApiService;
import org.example.service.EmployeeService;
import org.example.service.ImportService;
import org.example.exception.ApiException;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService();
        ImportService importService = new ImportService(employeeService);
        ApiService apiService = new ApiService();

        System.out.println("importowanie pracowników z pliku CSV:");
        ImportSummary importSummary = importService.importFromCsv("src/main/resources/employees.csv");
        System.out.println("Zaimportowano pracowników: " + importSummary.getImportedCount());
        System.out.println("bledy importu:");
        for (ImportSummary.ImportError error : importSummary.getErrors()) {
            System.out.println(error);
        }
        System.out.println();

        System.out.println("Pobieranie pracowników z api");
        try {
            List<Employee> apiEmployees = apiService.fetchEmployeesFromApi();
            for (Employee employee : apiEmployees) {
                employeeService.addEmployee(employee);
            }
            System.out.println("Pobrano " + apiEmployees.size() + " pracowników z API");
        } catch (ApiException e) {
            System.out.println("Błąd podczas pobierania danych z API: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Lista wszystkich pracownikow:");
        for (Employee employee : employeeService.getAllEmployees()) {
            System.out.printf("%s %s (%s) - %s, Pensja: %.2f%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getCompanyName(),
                    employee.getPosition(),
                    employee.getSalary());
        }
        System.out.println();

        System.out.println("Pracownicy z pensją poniżej stawki:");
        List<Employee> underpaidEmployees = employeeService.validateSalaryConsistency();
        for (Employee employee : underpaidEmployees) {
            System.out.printf("%s %s - aktualna pensja: %.2f, minimalna dla stanowiska %s: %.2f%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getSalary(),
                    employee.getPosition(),
                    employee.getPosition().getSalary());
        }
        System.out.println();

        System.out.println("Statystyki firm:");
        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        for (Map.Entry<String, CompanyStatistics> entry : statistics.entrySet()) {
            System.out.println("Firma: " + entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();
        }
    }
}