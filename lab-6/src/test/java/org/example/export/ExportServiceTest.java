package org.example.export;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {

    @Mock
    private FileWriter fileWriter;

    @Mock
    private DataFormatter dataFormatter;

    @Mock
    private Logger logger;

    @InjectMocks
    private ExportService exportService;

    @Test
    void shouldExportSuccessfullyOnFirstAttempt() throws IOException {
        // Arrange
        ExportData data = new ExportData("Test content", "json");
        String filename = "export.json";

        when(dataFormatter.format(data)).thenReturn("[JSON] Test content");

        // Act
        boolean result = exportService.export(data, filename);

        // Assert
        assertTrue(result);
        verify(fileWriter, times(1)).write(eq(filename), eq("[JSON] Test content"));
        verify(dataFormatter, times(1)).format(data);
        verify(logger, never()).error(anyString());
    }

    @Test
    void shouldRetryAndSucceedOnSecondAttempt() throws IOException {
        // Arrange
        ExportData data = new ExportData("Test content", "csv");
        String filename = "export.csv";

        when(dataFormatter.format(data)).thenReturn("[CSV] Test content");

        doThrow(new IOException("Connection timeout"))
            .doNothing()
            .when(fileWriter).write(eq(filename), eq("[CSV] Test content"));

        // Act
        boolean result = exportService.export(data, filename);

        // Assert
        assertTrue(result);
        verify(fileWriter, times(2)).write(eq(filename), eq("[CSV] Test content"));
        verify(logger, times(1)).error(contains("Attempt 1 failed"));
    }

    @Test
    void shouldFailAfterAllRetriesExhausted() throws IOException {
        // Arrange
        ExportData data = new ExportData("Test content", "xml");
        String filename = "export.xml";

        when(dataFormatter.format(data)).thenReturn("[XML] Test content");

        doThrow(new IOException("Disk full"))
            .when(fileWriter).write(eq(filename), eq("[XML] Test content"));

        // Act
        boolean result = exportService.export(data, filename);

        // Assert
        assertFalse(result);
        verify(fileWriter, times(3)).write(eq(filename), eq("[XML] Test content"));
        verify(logger, times(4)).error(anyString());
        verify(logger, times(1)).error(contains("All retry attempts exhausted"));
    }
}

