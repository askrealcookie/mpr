package org.example.model;

public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private Position position;
    private double salary;

    public Employee(String firstName, String lastName, String email, String companyName, Position position,  double salary) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Imię nie może być puste");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwisko nie może być puste");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email nie może być pusty");
        }
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwa firmy nie może być pusta");
        }
        if (position == null) {
            throw new IllegalArgumentException("Stanowisko nie może być null");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Wynagrodzenie musi być dodatnie");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.position = position;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Imię nie może być puste");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwisko nie może być puste");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email nie może być pusty");
        }
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwa firmy nie może być pusta");
        }
        this.companyName = companyName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Stanowisko nie może być null");
        }
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Wynagrodzenie musi być dodatnie");
        }
        this.salary = salary;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Employee p){
            return p.email.equals(this.email);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pracownik(");
        sb.append("name:").append(firstName).append(", ").append(lastName);
        sb.append(", email:").append(email);
        sb.append(", nazwaFirmy:").append(companyName);
        sb.append(", stanowisko:").append(position);
        sb.append(')');
        return sb.toString();
    }
}
