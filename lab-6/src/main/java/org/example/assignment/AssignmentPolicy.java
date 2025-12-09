package org.example.assignment;

import org.example.model.Employee;

import java.util.List;
import java.util.Optional;

public interface AssignmentPolicy {

    Optional<Employee> chooseAssignee(ProjectTask task, List<Employee> candidates);
}

