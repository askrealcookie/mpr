package org.example.training;

import java.time.LocalDate;

public class Training {

    private final String id;
    private final String name;
    private final LocalDate dueDate;

    public Training(String id, String name, LocalDate dueDate) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}

