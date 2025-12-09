package org.example.export;

import java.io.IOException;

public interface FileWriter {
    void write(String filename, String content) throws IOException;
}

