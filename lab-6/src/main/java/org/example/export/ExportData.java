package org.example.export;

public class ExportData {
    private final String content;
    private final String format;

    public ExportData(String content, String format) {
        this.content = content;
        this.format = format;
    }

    public String getContent() {
        return content;
    }

    public String getFormat() {
        return format;
    }
}