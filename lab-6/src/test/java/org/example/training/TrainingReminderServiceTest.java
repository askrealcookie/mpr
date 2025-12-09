package org.example.training;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.service.EmployeeService;
import org.example.time.Clock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingReminderServiceTest {

    @Test
    void shouldGenerateAndSendRemindersForEligibleEmployees() {
        // arrange
        LocalDate today = LocalDate.of(2024, 1, 1);
        ClockStub clockStub = new ClockStub(today);

        Training trainingSoon = new Training("TR1", "BHP", today.plusDays(5));
        TrainingRepositoryStub trainingRepoStub = new TrainingRepositoryStub(List.of(trainingSoon));

        Employee alice = new Employee("Alice", "Smith", "alice@example.com", "ACME", Position.Programista, 5000.0);
        Employee bob = new Employee("Bob", "Brown", "bob@example.com", "ACME", Position.Programista, 4500.0);
        EmployeeServiceStub employeeServiceStub = new EmployeeServiceStub(List.of(alice, bob));

        TrainingEligibilityServiceStub eligibilityStub = new TrainingEligibilityServiceStub(List.of(alice));

        TrainingReminderSenderSpy senderSpy = new TrainingReminderSenderSpy();

        TrainingReminderService service = new TrainingReminderService(
                trainingRepoStub,
                employeeServiceStub,
                eligibilityStub,
                senderSpy,
                clockStub,
                30
        );

        // act
        service.sendReminders();

        // assert
        TrainingReminder reminder = senderSpy.getSentReminders().get(0);
        assertAll(
                () -> assertEquals(1, senderSpy.getSentReminders().size()),
                () -> assertEquals(alice, reminder.getEmployee()),
                () -> assertEquals(trainingSoon, reminder.getTraining()),
                () -> assertEquals(5, reminder.getDaysUntilDue())
        );
    }

    @Test
    void shouldNotGenerateRemindersWhenNoTrainingsWithinThreshold() {
        LocalDate today = LocalDate.of(2024, 1, 1);
        ClockStub clockStub = new ClockStub(today);

        Training trainingFar = new Training("TR2", "RODO", today.plusDays(40));
        TrainingRepositoryStub trainingRepoStub = new TrainingRepositoryStub(List.of(trainingFar));

        Employee alice = new Employee("Alice", "Smith", "alice@example.com", "ACME", Position.Programista, 5000.0);
        EmployeeServiceStub employeeServiceStub = new EmployeeServiceStub(List.of(alice));
        TrainingEligibilityServiceStub eligibilityStub = new TrainingEligibilityServiceStub(List.of(alice));
        TrainingReminderSenderSpy senderSpy = new TrainingReminderSenderSpy();

        TrainingReminderService service = new TrainingReminderService(
                trainingRepoStub,
                employeeServiceStub,
                eligibilityStub,
                senderSpy,
                clockStub,
                30
        );

        service.sendReminders();

        assertTrue(senderSpy.getSentReminders().isEmpty());
    }


    static class ClockStub implements Clock {
        private final LocalDate fixedDate;

        ClockStub(LocalDate fixedDate) {
            this.fixedDate = fixedDate;
        }

        @Override
        public LocalDate today() {
            return fixedDate;
        }
    }

    static class TrainingRepositoryStub implements TrainingRepository {
        private final List<Training> trainings;

        TrainingRepositoryStub(List<Training> trainings) {
            this.trainings = new ArrayList<>(trainings);
        }

        @Override
        public List<Training> findAll() {
            return new ArrayList<>(trainings);
        }
    }

    static class EmployeeServiceStub extends EmployeeService {

        private final List<Employee> employees;

        EmployeeServiceStub(List<Employee> employees) {
            this.employees = new ArrayList<>(employees);
        }

        @Override
        public List<Employee> getAllEmployees() {
            return new ArrayList<>(employees);
        }
    }

    static class TrainingEligibilityServiceStub implements TrainingEligibilityService {
        private final List<Employee> eligible;

        TrainingEligibilityServiceStub(List<Employee> eligible) {
            this.eligible = new ArrayList<>(eligible);
        }

        @Override
        public boolean isTrainingRequiredForEmployee(Training training, Employee employee) {
            return eligible.contains(employee);
        }
    }

    static class TrainingReminderSenderSpy implements TrainingReminderSender {

        private final List<TrainingReminder> sentReminders = new ArrayList<>();

        @Override
        public void sendReminder(TrainingReminder reminder) {
            sentReminders.add(reminder);
        }

        List<TrainingReminder> getSentReminders() {
            return new ArrayList<>(sentReminders);
        }
    }
}
