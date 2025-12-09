package org.example.service;

import org.example.exception.InvalidDataException;
import org.example.model.Employee;
import org.example.model.Position;

public class PromotionService {

    public void promote(Employee employee, Position newPosition) throws InvalidDataException {
        if (employee == null) {
            throw new InvalidDataException("Pracownik nie może być null");
        }
        if (newPosition == null) {
            throw new InvalidDataException("Nowe stanowisko nie może być null");
        }

        Position current = employee.getPosition();
        if (current == null) {
            throw new InvalidDataException("Aktualne stanowisko nie może być null");
        }

        if (newPosition.ordinal() >= current.ordinal()) {
            throw new InvalidDataException("Nieprawidłowy awans: nowe stanowisko nie jest wyżej w hierarchii");
        }

        employee.setPosition(newPosition);
        employee.setSalary(newPosition.getSalary());
    }

    public void giveRaise(Employee employee, double percent) throws InvalidDataException {
        if (employee == null) {
            throw new InvalidDataException("Pracownik nie może być null");
        }
        if (percent < 0) {
            throw new InvalidDataException("Procent podwyżki nie może być ujemny");
        }

        double currentSalary = employee.getSalary();
        double newSalary = currentSalary + currentSalary * (percent / 100.0);
        employee.setSalary(newSalary);
    }
}