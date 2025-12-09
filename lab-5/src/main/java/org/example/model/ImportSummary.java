package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ImportSummary {
    private final int importedCount;
    private final List<ImportError> errors;

    public ImportSummary(int importedCount) {
        this.importedCount = importedCount;
        this.errors = new ArrayList<>();
    }

    public void addError(int lineNumber, String errorMessage) {
        errors.add(new ImportError(lineNumber, errorMessage));
    }

    public int getImportedCount() {
        return importedCount;
    }

    public List<ImportError> getErrors() {
        return new ArrayList<>(errors);
    }

    public static class ImportError {
        private final int lineNumber;
        private final String message;

        public ImportError(int lineNumber, String message) {
            this.lineNumber = lineNumber;
            this.message = message;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "linia " + lineNumber + ": " + message;
        }
    }
}