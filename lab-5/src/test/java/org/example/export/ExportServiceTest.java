package org.example.export;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportServiceTest {

    @Test
    void shouldExportOnlyEmployeesForGivenPositions() {
        // arrange
        Employee activeDev = new Employee("Alice", "Smith", "alice@example.com", "ACME", Position.Programista, 5000.0);
        Employee inactiveDev = new Employee("Bob", "Brown", "bob@example.com", "ACME", Position.Programista, 4500.0);
        Employee activeTester = new Employee("Carol", "White", "carol@example.com", "ACME", Position.Stazysta, 4000.0);

        EmployeeServiceStub employeeServiceStub = new EmployeeServiceStub(List.of(activeDev, inactiveDev, activeTester));
        EmployeeDataFormatterStub formatterStub = new EmployeeDataFormatterStub();

        ExportService service = new ExportService(employeeServiceStub, formatterStub);

        ExportRequest request = new ExportRequest(ExportFormat.CSV, false, List.of(Position.Programista.name()));

        // act
        ExportResult result = service.exportEmployees(request);

        // assert
        assertAll(
                () -> assertEquals(ExportFormat.CSV, result.getFormat()),
                () -> assertEquals(List.of(activeDev, inactiveDev), formatterStub.getLastEmployees()),
                () -> assertEquals(ExportFormat.CSV, formatterStub.getLastFormat()),
                () -> assertNotNull(result.getGeneratedAt())
        );
    }


    static class EmployeeServiceStub extends EmployeeService {

        private final List<Employee> employees;

        EmployeeServiceStub(List<Employee> employees) {
            this.employees = new ArrayList<>(employees);
        }

        @Override
        public List<Employee> getAllEmployees() {
            return new ArrayList<>(employees);
        }
    }

    static class EmployeeDataFormatterStub implements org.example.export.EmployeeDataFormatter {

        private List<Employee> lastEmployees;
        private ExportFormat lastFormat;

        @Override
        public String formatEmployees(List<Employee> employees, ExportFormat format) {
            this.lastEmployees = new ArrayList<>(employees);
            this.lastFormat = format;
            return "FORMATTED";
        }

        List<Employee> getLastEmployees() {
            return lastEmployees;
        }

        ExportFormat getLastFormat() {
            return lastFormat;
        }
    }
}
