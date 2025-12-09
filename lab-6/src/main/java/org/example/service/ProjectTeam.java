package org.example.service;

import org.example.model.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectTeam {

    private final String name;
    private final List<Employee> members = new ArrayList<>();

    public ProjectTeam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getMembers() {
        return Collections.unmodifiableList(members);
    }

    void addMember(Employee employee) {
        members.add(employee);
    }

    void removeMember(Employee employee) {
        members.remove(employee);
    }
}