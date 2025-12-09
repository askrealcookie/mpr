package org.example.training;

import org.example.model.Employee;

public class TrainingReminder {

    private final Employee employee;
    private final Training training;
    private final long daysUntilDue;

    public TrainingReminder(Employee employee, Training training, long daysUntilDue) {
        this.employee = employee;
        this.training = training;
        this.daysUntilDue = daysUntilDue;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Training getTraining() {
        return training;
    }

    public long getDaysUntilDue() {
        return daysUntilDue;
    }
}

