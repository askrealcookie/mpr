package org.example.assignment;

import org.example.model.Employee;

public class AssignmentResult {

    private final ProjectTask task;
    private final Employee employee;
    private final String failureReason;

    private AssignmentResult(ProjectTask task, Employee employee, String failureReason) {
        this.task = task;
        this.employee = employee;
        this.failureReason = failureReason;
    }

    public static AssignmentResult assigned(ProjectTask task, Employee employee) {
        return new AssignmentResult(task, employee, null);
    }

    public static AssignmentResult notAssigned(ProjectTask task, String failureReason) {
        return new AssignmentResult(task, null, failureReason);
    }

    public ProjectTask getTask() {
        return task;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public boolean isAssigned() {
        return employee != null;
    }
}

