package org.example;

public enum Position {
    Prezes(25000),
    Wiceprezes(18000),
    Manager(12000),
    Programista(8000),
    Stazysta(3000);

    private final double salary;

    Position(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}
