package org.example.assignment;

import java.util.List;

public class ProjectTask {

    private final String id;
    private final String name;
    private final List<String> requiredSkills;
    private final int estimatedHours;

    public ProjectTask(String id, String name, List<String> requiredSkills, int estimatedHours) {
        this.id = id;
        this.name = name;
        this.requiredSkills = requiredSkills;
        this.estimatedHours = estimatedHours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }
}

