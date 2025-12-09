package org.example.training;

import org.example.model.Employee;

public interface TrainingEligibilityService {

    boolean isTrainingRequiredForEmployee(Training training, Employee employee);
}

