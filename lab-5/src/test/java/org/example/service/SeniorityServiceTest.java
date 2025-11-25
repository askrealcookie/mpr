package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Testy analizy stazu pracy")
class SeniorityServiceTest {

    private SeniorityService seniorityService;
    private LocalDate now;

    @BeforeEach
    void setUp() {
        seniorityService = new SeniorityService();
        now = LocalDate.of(2024, 1, 1);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01,0",
            "2023-01-01,1",
            "2020-01-01,4",
            "2020-02-29,3"
    })
    void shouldCalculateYearsOfService(String hireDateStr, int expectedYears) {
        LocalDate hireDate = LocalDate.parse(hireDateStr);
        int years = seniorityService.getYearsOfService(hireDate, now);
        assertThat(years, is(expectedYears));
    }

    @Test
    void shouldFilterEmployeesBySeniorityRange() {
        Map<Employee, LocalDate> map = new HashMap<>();
        Employee e1 = new Employee("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000);
        Employee e2 = new Employee("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000);
        Employee e3 = new Employee("Piotr", "Zielinski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000);

        map.put(e1, LocalDate.of(2019, 1, 1));
        map.put(e2, LocalDate.of(2022, 1, 1));
        map.put(e3, LocalDate.of(2023, 6, 1));

        List<Employee> result = seniorityService.filterBySeniorityRange(map, 1, 4, now);

        assertThat(result, contains(e2));
        assertThat(result, not(hasItem(e1)));
        assertThat(result, not(hasItem(e3)));
    }

    @Test
    void shouldFindJubileeEmployees() {
        Map<Employee, LocalDate> map = new HashMap<>();
        Employee e1 = new Employee("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000);
        Employee e2 = new Employee("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000);
        Employee e3 = new Employee("Piotr", "Zielinski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000);

        map.put(e1, LocalDate.of(2019, 1, 1));
        map.put(e2, LocalDate.of(2014, 1, 1));
        map.put(e3, LocalDate.of(2023, 1, 1));

        List<Employee> jubilees = seniorityService.findJubilees(map, now);

        assertThat(jubilees, containsInAnyOrder(e1, e2));
        assertThat(jubilees, not(hasItem(e3)));
    }

    @Test
    void shouldBuildSeniorityReport() {
        Map<Employee, LocalDate> map = new HashMap<>();
        Employee e1 = new Employee("Jan", "Kowalski", "jan@example.com", "TechCorp", Position.Programista, 8000);
        Employee e2 = new Employee("Anna", "Nowak", "anna@example.com", "TechCorp", Position.Manager, 12000);
        Employee e3 = new Employee("Piotr", "Zielinski", "piotr@example.com", "TechCorp", Position.Stazysta, 3000);

        map.put(e1, LocalDate.of(2019, 1, 1));
        map.put(e2, LocalDate.of(2022, 1, 1));
        map.put(e3, LocalDate.of(2023, 1, 1));

        Map<Integer, Long> report = seniorityService.buildSeniorityReport(map, now);

        assertThat(report, hasEntry(5, 1L));
        assertThat(report, hasEntry(2, 1L));
        assertThat(report, hasEntry(1, 1L));
        assertThat(report.keySet(), everyItem(greaterThanOrEqualTo(1)));
    }
}