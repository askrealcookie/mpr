package org.example.export;

import java.util.List;

public class ExportRequest {

    private final ExportFormat format;
    private final boolean includeInactive;
    private final List<String> positionsCodes;

    public ExportRequest(ExportFormat format, boolean includeInactive, List<String> positionsCodes) {
        this.format = format;
        this.includeInactive = includeInactive;
        this.positionsCodes = positionsCodes;
    }

    public ExportFormat getFormat() {
        return format;
    }

    public boolean isIncludeInactive() {
        return includeInactive;
    }

    public List<String> getPositionsCodes() {
        return positionsCodes;
    }
}

