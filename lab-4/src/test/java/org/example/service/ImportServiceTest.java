package org.example.service;

import org.example.model.ImportSummary;
import org.example.model.Employee;
import org.example.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testty ImportService")
class ImportServiceTest {

    private EmployeeService employeeService;
    private ImportService importService;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        employeeService = new EmployeeService();
        importService = new ImportService(employeeService);
        tempFile = Files.createTempFile("employees-test", ".csv");
    }

    @AfterEach
    void tearDown() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    private void writeLines(String... lines) throws IOException {
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            for (String line : lines) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }
    }

    @Test
    void shouldImportValidEmployees() throws IOException {
        // Arrange
        writeLines(
                "firstName,lastName,email,company,position,salary",
                "Jan,Kowalski,jan@example.com,TechCorp,Programista,9000",
                "Anna,Nowak,anna@example.com,TechCorp,Manager,12000"
        );

        // Act
        ImportSummary summary = importService.importFromCsv(tempFile.toString());

        // Assert
        assertAll(
                () -> assertEquals(2, summary.getImportedCount()),
                () -> assertTrue(summary.getErrors().isEmpty()),
                () -> assertEquals(2, employeeService.getAllEmployees().size())
        );
    }

    @Test
    void shouldReturnErrorForInvalidEmail() throws IOException {
        // Arrange
        writeLines(
                "firstName,lastName,email,company,position,salary",
                "Jan,Kowalski,niepoprawnyEmail,TechCorp,Programista,9000"
        );

        // Act
        ImportSummary summary = importService.importFromCsv(tempFile.toString());

        // Assert
        assertAll(
                () -> assertEquals(0, summary.getImportedCount()),
                () -> assertEquals(1, summary.getErrors().size()),
                () -> assertTrue(summary.getErrors().get(0).getMessage().contains("Nieprawidłowy format e-maila")),
                () -> assertTrue(employeeService.getAllEmployees().isEmpty())
        );
    }

    @Test
    void shouldReturnErrorForInvalidPosition() throws IOException {
        // Arrange
        writeLines(
                "firstName,lastName,email,company,position,salary",
                "Jan,Kowalski,jan@example.com,TechCorp,NieMaTakiegoStanowiska,9000"
        );

        // Act
        ImportSummary summary = importService.importFromCsv(tempFile.toString());

        // Assert
        assertAll(
                () -> assertEquals(0, summary.getImportedCount()),
                () -> assertEquals(1, summary.getErrors().size()),
                () -> assertTrue(summary.getErrors().get(0).getMessage().contains("Nieprawidłowe stanowisko")),
                () -> assertTrue(employeeService.getAllEmployees().isEmpty())
        );
    }

    @Test
    void shouldReturnErrorForInvalidSalary() throws IOException {
        // Arrange
        writeLines(
                "firstName,lastName,email,company,position,salary",
                "Jan,Kowalski,jan@example.com,TechCorp,Programista,ABC"
        );

        // Act
        ImportSummary summary = importService.importFromCsv(tempFile.toString());

        // Assert
        assertAll(
                () -> assertEquals(0, summary.getImportedCount()),
                () -> assertEquals(1, summary.getErrors().size()),
                () -> assertTrue(summary.getErrors().get(0).getMessage().contains("Nieprawidłowy format wynagrodzenia")),
                () -> assertTrue(employeeService.getAllEmployees().isEmpty())
        );
    }

    @Test
    void shouldSkipEmptyLines() throws IOException {
        // Arrange
        writeLines(
                "firstName,lastName,email,company,position,salary",
                "",
                "Jan,Kowalski,jan@example.com,TechCorp,Programista,9000",
                "   ",
                "Anna,Nowak,anna@example.com,TechCorp,Manager,12000"
        );

        // Act
        ImportSummary summary = importService.importFromCsv(tempFile.toString());

        // Assert
        assertAll(
                () -> assertEquals(2, summary.getImportedCount()),
                () -> assertEquals(0, summary.getErrors().size()),
                () -> assertEquals(2, employeeService.getAllEmployees().size())
        );
    }
}

