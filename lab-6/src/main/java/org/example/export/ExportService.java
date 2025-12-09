package org.example.export;

import java.io.IOException;

public class ExportService {
    private final FileWriter fileWriter;
    private final DataFormatter dataFormatter;
    private final Logger logger;

    public ExportService(FileWriter fileWriter, DataFormatter dataFormatter, Logger logger) {
        this.fileWriter = fileWriter;
        this.dataFormatter = dataFormatter;
        this.logger = logger;
    }

    public boolean export(ExportData data, String filename) {
        String formattedContent = dataFormatter.format(data);

        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                fileWriter.write(filename, formattedContent);
                return true;
            } catch (IOException e) {
                logger.error("Attempt " + attempt + " failed: " + e.getMessage());

                if (attempt == maxRetries) {
                    logger.error("All retry attempts exhausted. Export failed.");
                    return false;
                }
            }
        }

        return false;
    }
}

