package org.example.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.ImportSummary;
import org.example.exception.InvalidDataException;

import java.io.FileReader;
import java.io.IOException;

public class ImportService {
    private final EmployeeService employeeService;

    public ImportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ImportSummary importFromCsv(String filePath) {
        int successfulImports = 0;
        ImportSummary summary = new ImportSummary(0);
        
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.readNext();
            
            String[] nextLine;
            int lineNumber = 1;
            
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                if (nextLine.length == 0 || (nextLine.length == 1 && nextLine[0].trim().isEmpty())) {
                    continue;
                }

                try {
                    Employee employee = parseRow(nextLine, lineNumber);
                    employeeService.addEmployee(employee);
                    successfulImports++;
                } catch (InvalidDataException e) {
                    summary.addError(e.getLineNumber(), e.getMessage());
                }
            }
        } catch (IOException e) {
            summary.addError(0, "Błąd odczytu pliku: " + e.getMessage());
        } catch (CsvValidationException e) {
            summary.addError(0, "Błąd parsowania CSV: " + e.getMessage());
        }

        return summaryWithCount(summary, successfulImports);
    }

    private Employee parseRow(String[] row, int lineNumber) throws InvalidDataException {
        if (row.length != 6) {
            throw new InvalidDataException("Niepoprawna liczba pól (oczekiwano 6, otrzymano " + row.length + ")", lineNumber);
        }

        String firstName = row[0].trim();
        if (firstName.isEmpty()) {
            throw new InvalidDataException("Imię nie może być puste", lineNumber);
        }

        String lastName = row[1].trim();
        if (lastName.isEmpty()) {
            throw new InvalidDataException("Nazwisko nie może być puste", lineNumber);
        }

        String email = row[2].trim();
        if (!email.contains("@")) {
            throw new InvalidDataException("Nieprawidłowy format e-maila", lineNumber);
        }

        String company = row[3].trim();
        if (company.isEmpty()) {
            throw new InvalidDataException("Nazwa firmy nie może być pusta", lineNumber);
        }

        Position position;
        try {
            position = Position.valueOf(row[4].trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Nieprawidłowe stanowisko: " + row[4], lineNumber);
        }

        double salary;
        try {
            salary = Double.parseDouble(row[5].trim());
            if (salary <= 0) {
                throw new InvalidDataException("Wynagrodzenie musi być dodatnie", lineNumber);
            }
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Nieprawidłowy format wynagrodzenia: " + row[5], lineNumber);
        }

        return new Employee(firstName, lastName, email, company, position, salary);
    }

    private ImportSummary summaryWithCount(ImportSummary base, int importedCount) {
        ImportSummary result = new ImportSummary(importedCount);
        for (ImportSummary.ImportError err : base.getErrors()) {
            result.addError(err.getLineNumber(), err.getMessage());
        }
        return result;
    }
}