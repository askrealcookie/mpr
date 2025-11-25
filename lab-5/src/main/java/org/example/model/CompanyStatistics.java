package org.example.model;

public class CompanyStatistics {
    private final int employeeCount;
    private final double averageSalary;
    private final String topEarnerFullName;

    public CompanyStatistics(int employeeCount, double averageSalary, String topEarnerFullName) {
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.topEarnerFullName = topEarnerFullName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public String getTopEarnerFullName() {
        return topEarnerFullName;
    }

    @Override
    public String toString() {
        return String.format("CompanyStatistics{employeeCount=%d, averageSalary=%.2f, topEarner='%s'}", 
            employeeCount, averageSalary, topEarnerFullName);
    }
}