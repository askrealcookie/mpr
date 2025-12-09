package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeamService {

    private final Map<String, ProjectTeam> teams = new HashMap<>();
    private final int maxSize;
    private final int minDifferentPositions;

    public TeamService(int maxSize, int minDifferentPositions) {
        this.maxSize = maxSize;
        this.minDifferentPositions = minDifferentPositions;
    }

    public ProjectTeam createTeam(String name, List<Employee> initialMembers) {
        if (teams.containsKey(name)) {
            throw new IllegalArgumentException("team exists");
        }
        if (initialMembers == null) {
            initialMembers = new ArrayList<>();
        }
        if (initialMembers.size() > maxSize) {
            throw new IllegalArgumentException("team too big");
        }
        if (!isDiversityOk(initialMembers)) {
            throw new IllegalArgumentException("not enough different positions");
        }
        ProjectTeam team = new ProjectTeam(name);
        for (Employee e : initialMembers) {
            team.addMember(e);
        }
        teams.put(name, team);
        return team;
    }

    public ProjectTeam getTeam(String name) {
        return teams.get(name);
    }

    public void moveEmployee(Employee employee, String fromTeamName, String toTeamName) {
        if (employee == null) {
            throw new IllegalArgumentException("employee null");
        }
        ProjectTeam from = teams.get(fromTeamName);
        ProjectTeam to = teams.get(toTeamName);
        if (from == null || to == null) {
            throw new IllegalArgumentException("team not found");
        }
        if (!from.getMembers().contains(employee)) {
            throw new IllegalArgumentException("employee not in source team");
        }
        if (to.getMembers().contains(employee)) {
            throw new IllegalArgumentException("employee already in target team");
        }
        if (to.getMembers().size() >= maxSize) {
            throw new IllegalArgumentException("target team full");
        }
        from.removeMember(employee);
        to.addMember(employee);
    }

    private boolean isDiversityOk(List<Employee> members) {
        if (members.isEmpty()) {
            return true;
        }
        Set<Position> positions = new HashSet<>();
        for (Employee e : members) {
            positions.add(e.getPosition());
        }
        return positions.size() >= minDifferentPositions;
    }
}

