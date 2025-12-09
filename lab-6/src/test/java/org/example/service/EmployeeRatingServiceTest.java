package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testy systemu ocen pracowniczych")
class EmployeeRatingServiceTest {

    private EmployeeRatingService ratingService;
    private Employee e1;
    private Employee e2;

    @BeforeEach
    void setUp() {
        ratingService = new EmployeeRatingService();
        e1 = new Employee("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000);
        e2 = new Employee("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000);
    }

    @ParameterizedTest
    @CsvSource({
            "5,0,5.0",
            "4;4;4,;,4.0",
            "3;5,;,4.0"
    })
    void shouldCalculateAverageRating(String ratingsStr, String separator, double expectedAverage) {
        String sep = separator.isEmpty() ? ";" : separator;
        String[] parts = ratingsStr.split(sep);
        for (String p : parts) {
            int r = Integer.parseInt(p);
            ratingService.addRating(e1, r);
        }
        double avg = ratingService.getAverageRating(e1);
        assertThat(avg).isEqualTo(expectedAverage);
    }

    @Test
    void shouldStoreRatingHistoryPerEmployee() {
        ratingService.addRating(e1, 5);
        ratingService.addRating(e1, 4);
        ratingService.addRating(e2, 3);

        Map<Employee, List<Integer>> all = ratingService.getAllRatings();

        assertThat(all)
                .containsKey(e1)
                .containsKey(e2);

        assertThat(all.get(e1)).containsExactly(5, 4);
        assertThat(all.get(e2)).containsExactly(3);
    }

    @Test
    void shouldFindTopRatedEmployees() {
        ratingService.addRating(e1, 5);
        ratingService.addRating(e1, 4);
        ratingService.addRating(e2, 3);
        ratingService.addRating(e2, 5);

        List<Employee> top = ratingService.getTopRatedEmployees();

        assertThat(top)
                .hasSize(1)
                .containsExactly(e1);
    }

    @Test
    void shouldReturnEmptyTopListWhenNoRatings() {
        List<Employee> top = ratingService.getTopRatedEmployees();
        assertThat(top).isEmpty();
    }
}
