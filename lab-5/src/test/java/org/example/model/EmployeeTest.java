package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy klasy Employee")
class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(
                "Jan",
                "Kowalski",
                "jan.kowalski@example.com",
                "TechCorp",
                Position.Programista,
                9000.0
        );
    }

    @Test
    void shouldCreateEmployeeWithAllFields() {
        // Assert
        assertAll(
                () -> assertEquals("Jan", employee.getFirstName()),
                () -> assertEquals("Kowalski", employee.getLastName()),
                () -> assertEquals("jan.kowalski@example.com", employee.getEmail()),
                () -> assertEquals("TechCorp", employee.getCompanyName()),
                () -> assertEquals(Position.Programista, employee.getPosition()),
                () -> assertEquals(9000.0, employee.getSalary())
        );
    }

    @Test
    void shouldReturnFullName() {
        // Act
        String fullName = employee.getFullName();

        // Assert
        assertEquals("Jan Kowalski", fullName);
    }

    @Test
    void shouldUpdateFirstName() {
        // Act
        employee.setFirstName("Piotr");

        // Assert
        assertEquals("Piotr", employee.getFirstName());
    }

    @Test
    void shouldUpdateLastName() {
        // Act
        employee.setLastName("Nowak");

        // Assert
        assertEquals("Nowak", employee.getLastName());
    }

    @Test
    void shouldUpdateEmail() {
        // Act
        employee.setEmail("piotr.nowak@example.com");

        // Assert
        assertEquals("piotr.nowak@example.com", employee.getEmail());
    }

    @Test
    void shouldUpdateCompanyName() {
        // Act
        employee.setCompanyName("NewCorp");

        // Assert
        assertEquals("NewCorp", employee.getCompanyName());
    }

    @Test
    void shouldUpdatePosition() {
        // Act
        employee.setPosition(Position.Manager);

        // Assert
        assertEquals(Position.Manager, employee.getPosition());
    }

    @Test
    void shouldUpdateSalary() {
        // Act
        employee.setSalary(12000.0);

        // Assert
        assertEquals(12000.0, employee.getSalary());
    }

    @Test
    void shouldBeEqualWhenEmailsAreTheSame() {
        // Arrange
        Employee employee1 = new Employee("Jan", "Kowalski", "test@example.com", "Corp1", Position.Programista, 8000);
        Employee employee2 = new Employee("Anna", "Nowak", "test@example.com", "Corp2", Position.Manager, 12000);

        // Assert
        assertEquals(employee1, employee2);
    }

    @Test
    void shouldNotBeEqualWhenEmailsAreDifferent() {
        // Arrange
        Employee employee1 = new Employee("Jan", "Kowalski", "jan@example.com", "Corp1", Position.Programista, 8000);
        Employee employee2 = new Employee("Jan", "Kowalski", "piotr@example.com", "Corp1", Position.Programista, 8000);

        // Assert
        assertNotEquals(employee1, employee2);
    }

    @Test
    @DisplayName("Pracownik nie powinien być równy null")
    void shouldNotBeEqualToNull() {
        // Assert
        assertNotEquals(null, employee);
    }

    @Test
    void shouldNotBeEqualToObjectOfDifferentType() {
        // Arrange
        String notAnEmployee = "Not an employee";

        // Assert
        assertNotEquals(employee, notAnEmployee);
    }

    @Test
    void shouldBeEqualToItself() {
        // Assert
        assertEquals(employee, employee);
    }

    @Test
    void shouldHaveSameHashCodeWhenEmailsAreTheSame() {
        // Arrange
        Employee employee1 = new Employee("Jan", "Kowalski", "test@example.com", "Corp1", Position.Programista, 8000);
        Employee employee2 = new Employee("Anna", "Nowak", "test@example.com", "Corp2", Position.Manager, 12000);

        // Assert
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void shouldHaveDifferentHashCodeWhenEmailsAreDifferent() {
        // Arrange
        Employee employee1 = new Employee("Jan", "Kowalski", "jan@example.com", "Corp1", Position.Programista, 8000);
        Employee employee2 = new Employee("Jan", "Kowalski", "piotr@example.com", "Corp1", Position.Programista, 8000);

        // Assert
        assertNotEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void shouldReturnStringRepresentation() {
        // Act
        String result = employee.toString();

        // Assert
        assertAll(
                () -> assertTrue(result.contains("Jan")),
                () -> assertTrue(result.contains("Kowalski")),
                () -> assertTrue(result.contains("jan.kowalski@example.com")),
                () -> assertTrue(result.contains("TechCorp")),
                () -> assertTrue(result.contains("Programista"))
        );
    }
}

