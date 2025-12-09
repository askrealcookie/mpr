package org.example.notification;

public class Certificate {
    private final String employeeId;
    private final String trainingName;
    private final String expirationDate;

    public Certificate(String employeeId, String trainingName, String expirationDate) {
        this.employeeId = employeeId;
        this.trainingName = trainingName;
        this.expirationDate = expirationDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
