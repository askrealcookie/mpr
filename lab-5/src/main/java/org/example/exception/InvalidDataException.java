package org.example.exception;

public class InvalidDataException extends Exception {
    private final int lineNumber;

    public InvalidDataException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}