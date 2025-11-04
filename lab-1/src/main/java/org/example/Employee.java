package org.example;

public class Employee {
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private Position position;

    public Employee(String firstName, String lastName, String email, String companyName, Position position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyName = companyName;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
