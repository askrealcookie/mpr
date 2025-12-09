package org.example.export;

public class DataFormatter {
    public String format(ExportData data) {
        return String.format("[%s] %s", data.getFormat().toUpperCase(), data.getContent());
    }
}

