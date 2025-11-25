package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.CompanyStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy EmployeeService")
class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    private Employee emp(String first, String last, String email, String company, Position position, double salary) {
        return new Employee(first, last, email, company, position, salary);
    }

    @Test
    void shouldAddNewEmployee() {
        // Arrange
        Employee employee = emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 9000);

        // Act
        employeeService.addEmployee(employee);

        // Assert
        List<Employee> all = employeeService.getAllEmployees();
        assertEquals(1, all.size());
        assertEquals("jan@example.com", all.get(0).getEmail());
    }

    @Test
    void shouldFindEmployeesByCompany() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 9000));
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000));
        employeeService.addEmployee(emp("Piotr", "Zielinski", "piotr@example.com", "OtherCorp", Position.Stazysta, 3000));

        // Act
        List<Employee> techCorpEmployees = employeeService.findByCompany("TechCorp");

        // Assert
        assertAll(
                () -> assertEquals(2, techCorpEmployees.size()),
                () -> assertTrue(techCorpEmployees.stream().allMatch(e -> e.getCompanyName().equals("TechCorp")))
        );
    }

    @Test
    void shouldReturnEmptyListForUnknownCompany() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 9000));

        // Act
        List<Employee> result = employeeService.findByCompany("NieIstnieje");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSortEmployeesByLastName() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 9000));
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000));
        employeeService.addEmployee(emp("Piotr", "Adamski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000));

        // Act
        List<Employee> sorted = employeeService.sortByLastName();

        // Assert
        assertEquals("Adamski", sorted.get(0).getLastName());
        assertEquals("Kowalski", sorted.get(1).getLastName());
        assertEquals("Nowak", sorted.get(2).getLastName());
    }

    @Test
    void shouldReturnEmptyListWhenSortingEmptyList() {
        // Act
        List<Employee> sorted = employeeService.sortByLastName();

        // Assert
        assertNotNull(sorted);
        assertTrue(sorted.isEmpty());
    }

    @Test
    void shouldGroupEmployeesByPosition() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 9000));
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Programista, 9500));
        employeeService.addEmployee(emp("Piotr", "Zielinski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000));

        // Act
        Map<Position, List<Employee>> grouped = employeeService.groupByPosition();

        // Assert
        assertAll(
                () -> assertEquals(2, grouped.get(Position.Programista).size()),
                () -> assertEquals(1, grouped.get(Position.Stazysta).size()),
                () -> assertFalse(grouped.containsKey(Position.Prezes))
        );
    }

    @Test
    void shouldReturnEmptyMapWhenGroupingEmptyList() {
        // Act
        Map<Position, List<Employee>> grouped = employeeService.groupByPosition();

        // Assert
        assertNotNull(grouped);
        assertTrue(grouped.isEmpty());
    }

    @Test
    void shouldCountEmployeesByPosition() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 9000));
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Programista, 9500));
        employeeService.addEmployee(emp("Piotr", "Zielinski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000));

        // Act
        Map<Position, Long> counts = employeeService.countByPosition();

        // Assert
        assertAll(
                () -> assertEquals(2L, counts.get(Position.Programista)),
                () -> assertEquals(1L, counts.get(Position.Stazysta)),
                () -> assertNull(counts.get(Position.Prezes))
        );
    }

    @Test
    void shouldReturnEmptyMapWhenCountingEmptyList() {
        // Act
        Map<Position, Long> counts = employeeService.countByPosition();

        // Assert
        assertNotNull(counts);
        assertTrue(counts.isEmpty());
    }

    @Test
    void shouldCalculateAverageSalary() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000));
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000));

        // Act
        double average = employeeService.getAverageSalary();

        // Assert
        assertEquals(10000.0, average, 0.001);
    }

    @Test
    void shouldReturnZeroAverageForEmptyList() {
        // Act
        double average = employeeService.getAverageSalary();

        // Assert
        assertEquals(0.0, average);
    }

    @Test
    void shouldReturnTopEarner() {
        // Arrange
        Employee e1 = emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000);
        Employee e2 = emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 15000);
        Employee e3 = emp("Piotr", "Zielinski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000);
        employeeService.addEmployee(e1);
        employeeService.addEmployee(e2);
        employeeService.addEmployee(e3);

        // Act
        Employee top = employeeService.getTopEarner();

        // Assert
        assertEquals("anna@example.com", top.getEmail());
    }

    @Test
    void shouldReturnNullTopEarnerForEmptyList() {
        // Act
        Employee top = employeeService.getTopEarner();

        // Assert
        assertNull(top);
    }

    @Test
    void shouldReturnEmployeesWithInconsistentSalary() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 7000)); // za mało
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Programista, 8000));  // ok

        // Act
        List<Employee> inconsistent = employeeService.validateSalaryConsistency();

        // Assert
        assertAll(
                () -> assertEquals(1, inconsistent.size()),
                () -> assertEquals("jan@example.com", inconsistent.get(0).getEmail())
        );
    }

    @Test
    void shouldBuildCompanyStatistics() {
        // Arrange
        employeeService.addEmployee(emp("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000));
        employeeService.addEmployee(emp("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000));
        employeeService.addEmployee(emp("Piotr", "Zielinski", "piotr@example.com", "OtherCorp", Position.Stazysta, 3000));

        // Act
        Map<String, CompanyStatistics> stats = employeeService.getCompanyStatistics();

        // Assert
        CompanyStatistics techStats = stats.get("TechCorp");
        CompanyStatistics otherStats = stats.get("OtherCorp");

        assertAll(
                () -> assertEquals(2, techStats.getEmployeeCount()),
                () -> assertEquals(10000.0, techStats.getAverageSalary(), 0.001),
                () -> assertEquals("Anna Nowak", techStats.getTopEarnerFullName()),
                () -> assertEquals(1, otherStats.getEmployeeCount()),
                () -> assertEquals(3000.0, otherStats.getAverageSalary(), 0.001),
                () -> assertEquals("Piotr Zielinski", otherStats.getTopEarnerFullName())
        );
    }
}

