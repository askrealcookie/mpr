package org.example.export;

import java.time.LocalDateTime;

public class ExportResult {

    private final String content;
    private final ExportFormat format;
    private final LocalDateTime generatedAt;

    public ExportResult(String content, ExportFormat format, LocalDateTime generatedAt) {
        this.content = content;
        this.format = format;
        this.generatedAt = generatedAt;
    }

    public String getContent() {
        return content;
    }

    public ExportFormat getFormat() {
        return format;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}

