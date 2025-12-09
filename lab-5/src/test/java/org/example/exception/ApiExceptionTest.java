package org.example.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy klasy ApiException")
class ApiExceptionTest {

    @Test
    void shouldStoreMessage() {
        // Arrange
        String message = "Błąd API";

        // Act
        ApiException exception = new ApiException(message);

        // Assert
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void shouldStoreMessageAndCause() {
        // Arrange
        String message = "Błąd API z przyczyną";
        Throwable cause = new RuntimeException("Przyczyna błędu");

        // Act
        ApiException exception = new ApiException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}

