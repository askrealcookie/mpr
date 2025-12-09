package org.example.assignment;

import org.example.model.Employee;

import java.util.List;
import java.util.Optional;

public class AssignmentService {

    private final AssignmentPolicy assignmentPolicy;
    private final AvailableEmployeeProvider availableEmployeeProvider;
    private final AssignmentNotifier assignmentNotifier;

    public AssignmentService(AssignmentPolicy assignmentPolicy,
                             AvailableEmployeeProvider availableEmployeeProvider,
                             AssignmentNotifier assignmentNotifier) {
        this.assignmentPolicy = assignmentPolicy;
        this.availableEmployeeProvider = availableEmployeeProvider;
        this.assignmentNotifier = assignmentNotifier;
    }

    /**
     * Finds the first available employee that matches task requirements and assigns the task.
     */
    public AssignmentResult assignTask(ProjectTask task) {
        List<Employee> candidates = availableEmployeeProvider.findAvailableEmployees(task.getEstimatedHours());
        Optional<Employee> chosen = assignmentPolicy.chooseAssignee(task, candidates);
        if (chosen.isEmpty()) {
            return AssignmentResult.notAssigned(task, "NO_MATCHING_EMPLOYEE");
        }
        Employee employee = chosen.get();
        assignmentNotifier.notifyAssignment(employee, task);
        return AssignmentResult.assigned(task, employee);
    }
}

