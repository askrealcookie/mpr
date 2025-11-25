package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("test zarzadzania zesplami")
class TeamServiceTest {

    private TeamService teamService;

    private Employee emp(String email, Position position) {
        return new Employee("Jan", "Kowalski", email, "TechCorp", position, position.getSalary());
    }

    @BeforeEach
    void setUp() {
        teamService = new TeamService(3, 2);
    }

    @ParameterizedTest
    @CsvSource({
            "Programista,Manager,true",
            "Programista,Programista,false"
    })
    void shouldValidateTeamDiversity(String pos1, String pos2, boolean expectedValid) {
        Employee e1 = emp("a@ex.com", Position.valueOf(pos1));
        Employee e2 = emp("b@ex.com", Position.valueOf(pos2));
        List<Employee> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        if (expectedValid) {
            ProjectTeam team = teamService.createTeam("T1" + pos1 + pos2, list);
            assertThat(team.getMembers()).extracting(Employee::getPosition)
                    .containsExactlyInAnyOrder(e1.getPosition(), e2.getPosition());
        } else {
            assertThatThrownBy(() -> teamService.createTeam("T1" + pos1 + pos2, list))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("not enough different positions");
        }
    }

    @Test
    void shouldNotCreateTeamWhenTooManyMembers() {
        List<Employee> list = new ArrayList<>();
        list.add(emp("a@ex.com", Position.Programista));
        list.add(emp("b@ex.com", Position.Manager));
        list.add(emp("c@ex.com", Position.Stazysta));
        list.add(emp("d@ex.com", Position.Stazysta));

        assertThatThrownBy(() -> teamService.createTeam("Big", list))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("team too big");
    }

    @Test
    void shouldMoveEmployeeBetweenTeams() {
        Employee e1 = emp("a@ex.com", Position.Programista);
        Employee e2 = emp("b@ex.com", Position.Manager);
        List<Employee> base = List.of(e1, e2);

        ProjectTeam t1 = teamService.createTeam("A", new ArrayList<>(base));
        ProjectTeam t2 = teamService.createTeam("B", new ArrayList<>(List.of(emp("c@ex.com", Position.Stazysta), emp("d@ex.com", Position.Manager))));

        teamService.moveEmployee(e1, "A", "B");

        assertThat(t1.getMembers()).doesNotContain(e1);
        assertThat(t2.getMembers()).contains(e1);
    }

    @Test
    void shouldNotMoveEmployeeWhenNotInSourceTeam() {
        Employee e1 = emp("a@ex.com", Position.Programista);
        Employee e2 = emp("b@ex.com", Position.Manager);
        Employee e3 = emp("c@ex.com", Position.Stazysta);

        ProjectTeam t1 = teamService.createTeam("A", List.of(e1, e2));
        ProjectTeam t2 = teamService.createTeam("B", List.of(e3, emp("d@ex.com", Position.Manager)));

        assertThatThrownBy(() -> teamService.moveEmployee(e3, "A", "B"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("employee not in source team");
    }

    @Test
    void shouldNotMoveEmployeeWhenAlreadyInTargetTeam() {
        Employee e1 = emp("a@ex.com", Position.Programista);
        Employee e2 = emp("b@ex.com", Position.Manager);
        Employee e3 = emp("c@ex.com", Position.Stazysta);

        ProjectTeam t1 = teamService.createTeam("A", List.of(e1, e2));
        ProjectTeam t2 = teamService.createTeam("B", List.of(e2, e3));
        t2.addMember(e1);

        assertThatThrownBy(() -> teamService.moveEmployee(e1, "A", "B"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("employee already in target team");
    }

    @Test
    void shouldNotMoveEmployeeWhenTargetTeamIsFull() {
        Employee e1 = emp("a@ex.com", Position.Programista);
        Employee e2 = emp("b@ex.com", Position.Manager);
        Employee e3 = emp("c@ex.com", Position.Stazysta);

        ProjectTeam t1 = teamService.createTeam("A", List.of(e1, e2));
        ProjectTeam t2 = teamService.createTeam("B", List.of(e3, emp("d@ex.com", Position.Manager), emp("e@ex.com", Position.Stazysta)));

        assertThatThrownBy(() -> teamService.moveEmployee(e1, "A", "B"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("target team full");
    }
}
