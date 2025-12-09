package org.example.assignment;

import org.example.model.Employee;
import org.example.model.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentServiceTest {

    @Test
    void shouldAssignFirstEmployeeReturnedByPolicyAndSendNotification() {
        // arrange
        Employee alice = new Employee("Alice", "Smith", "alice@example.com", "ACME", Position.Programista, 5000.0);
        ProjectTask task = new ProjectTask("T1", "Implement feature X", List.of("JAVA"), 8);

        AvailableEmployeeProviderStub providerStub = new AvailableEmployeeProviderStub(List.of(alice));
        AssignmentPolicyStub policyStub = new AssignmentPolicyStub(Optional.of(alice));
        AssignmentNotifierSpy notifierSpy = new AssignmentNotifierSpy();

        AssignmentService service = new AssignmentService(policyStub, providerStub, notifierSpy);

        // act
        AssignmentResult result = service.assignTask(task);

        // assert
        AssignmentNotifierSpy.Notification notification = notifierSpy.getNotifications().get(0);
        assertAll(
                () -> assertTrue(result.isAssigned()),
                () -> assertEquals(alice, result.getEmployee()),
                () -> assertEquals(1, notifierSpy.getNotifications().size()),
                () -> assertEquals(alice, notification.employee()),
                () -> assertEquals(task, notification.task())
        );
    }

    @Test
    void shouldReturnNotAssignedWhenNoCandidates() {
        ProjectTask task = new ProjectTask("T2", "Bugfix", List.of("JAVA"), 4);

        AvailableEmployeeProviderStub providerStub = new AvailableEmployeeProviderStub(Collections.emptyList());
        AssignmentPolicyStub policyStub = new AssignmentPolicyStub(Optional.empty());
        AssignmentNotifierSpy notifierSpy = new AssignmentNotifierSpy();

        AssignmentService service = new AssignmentService(policyStub, providerStub, notifierSpy);

        AssignmentResult result = service.assignTask(task);

        assertFalse(result.isAssigned());
        assertNull(result.getEmployee());
        assertEquals("NO_MATCHING_EMPLOYEE", result.getFailureReason());
        assertTrue(notifierSpy.getNotifications().isEmpty());
    }

    static class AvailableEmployeeProviderStub implements AvailableEmployeeProvider {

        private final List<Employee> available;

        AvailableEmployeeProviderStub(List<Employee> available) {
            this.available = new ArrayList<>(available);
        }

        @Override
        public List<Employee> findAvailableEmployees(int estimatedHours) {
            return new ArrayList<>(available);
        }
    }

    static class AssignmentPolicyStub implements AssignmentPolicy {

        private final Optional<Employee> chosen;

        AssignmentPolicyStub(Optional<Employee> chosen) {
            this.chosen = chosen;
        }

        @Override
        public Optional<Employee> chooseAssignee(ProjectTask task, List<Employee> candidates) {
            return chosen;
        }
    }

    static class AssignmentNotifierSpy implements AssignmentNotifier {

        record Notification(Employee employee, ProjectTask task) {}

        private final List<Notification> notifications = new ArrayList<>();

        @Override
        public void notifyAssignment(Employee employee, ProjectTask task) {
            notifications.add(new Notification(employee, task));
        }

        List<Notification> getNotifications() {
            return new ArrayList<>(notifications);
        }
    }
}
