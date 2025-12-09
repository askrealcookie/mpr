package org.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy  Position")
class PositionTest {

    @Test
    @DisplayName("Enum powinien mieć dokładnie 5 stanowisk")
    void shouldHaveFivePositions() {
        // Act
        Position[] positions = Position.values();

        // Assert
        assertEquals(5, positions.length);
    }

    @Test
    void shouldHaveHighestSalaryForPrezes() {
        // Assert
        assertEquals(25000, Position.Prezes.getSalary());
    }

    @Test
    void shouldHaveCorrectSalaryForWiceprezes() {
        // Assert
        assertEquals(18000, Position.Wiceprezes.getSalary());
    }

    @Test
    void shouldHaveCorrectSalaryForManager() {
        // Assert
        assertEquals(12000, Position.Manager.getSalary());
    }

    @Test
    void shouldHaveCorrectSalaryForProgramista() {
        // Assert
        assertEquals(8000, Position.Programista.getSalary());
    }

    @Test
    void shouldHaveLowestSalaryForStazysta() {
        // Assert
        assertEquals(3000, Position.Stazysta.getSalary());
    }

    @ParameterizedTest
    @EnumSource(Position.class)
    void shouldHavePositiveSalaryForAllPositions(Position position) {
        // Assert
        assertTrue(position.getSalary() > 0,
                "Pensja dla stanowiska " + position + " powinna być dodatnia");
    }

    @Test
    void shouldConvertStringToEnum() {
        // Act
        Position position = Position.valueOf("Programista");

        // Assert
        assertEquals(Position.Programista, position);
    }

    @Test
    void shouldThrowExceptionForInvalidPositionName() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Position.valueOf("brak");
        });
    }

    @Test
    void shouldHaveSalaryHierarchyFromHighestToLowest() {
        // Assert
        assertAll(
                () -> assertTrue(Position.Prezes.getSalary() > Position.Wiceprezes.getSalary()),
                () -> assertTrue(Position.Wiceprezes.getSalary() > Position.Manager.getSalary()),
                () -> assertTrue(Position.Manager.getSalary() > Position.Programista.getSalary()),
                () -> assertTrue(Position.Programista.getSalary() > Position.Stazysta.getSalary())
        );
    }

    @Test
    void shouldHaveSignificantSalaryDifference() {
        // Arrange
        double highest = Position.Prezes.getSalary();
        double lowest = Position.Stazysta.getSalary();

        // Assert
        assertTrue(highest > lowest * 2);
    }
}

