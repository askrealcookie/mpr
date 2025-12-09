package org.example.service;

import org.example.exception.InvalidDataException;
import org.example.model.Employee;
import org.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy podwyzek")
class PromotionServiceTest {

    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        promotionService = new PromotionService();
    }

    private Employee emp(Position position, double salary) {
        return new Employee("Jan", "Kowalski", "jan@firma.pl", "TechCorp", position, salary);
    }

    @Test
    void shouldPromoteEmployeeToHigherPosition() throws InvalidDataException {
        // Arrange
        Employee employee = emp(Position.Manager, 12000);

        // Act
        promotionService.promote(employee, Position.Prezes);

        // Assert
        assertEquals(Position.Prezes, employee.getPosition());
        assertEquals(Position.Prezes.getSalary(), employee.getSalary());
    }

    @Test
    void shouldThrowExceptionWhenNewPositionIsNull() {
        // Arrange
        Employee employee = emp(Position.Programista, 8000);

        // Act & Assert
        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> promotionService.promote(employee, null));
        assertEquals("Nowe stanowisko nie może być null", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Prezes, Manager",
            "Manager, Manager"
    })
    void shouldThrowExceptionWhenPromotionIsNotHigher(String currentPos, String newPos) {
        // Arrange
        Position current = Position.valueOf(currentPos);
        Position target = Position.valueOf(newPos);
        Employee employee = emp(current, current.getSalary());

        // Act & Assert
        assertThrows(InvalidDataException.class,
                () -> promotionService.promote(employee, target));
    }

    @Test
    void shouldGiveRaise() throws InvalidDataException {
        // Arrange
        Employee employee = emp(Position.Programista, 8000);

        // Act
        promotionService.giveRaise(employee, 10); // 10%

        // Assert
        assertEquals(8800, employee.getSalary(), 0.001);
    }

    @Test
    void shouldNotAllowNegativeRaise() {
        // Arrange
        Employee employee = emp(Position.Programista, 8000);

        // Act & Assert
        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> promotionService.giveRaise(employee, -5));
        assertEquals("Procent podwyżki nie może być ujemny", ex.getMessage());
    }
}
