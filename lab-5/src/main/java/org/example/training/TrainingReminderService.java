package org.example.training;

import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.example.time.Clock;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TrainingReminderService {

    private final TrainingRepository trainingRepository;
    private final EmployeeService employeeService;
    private final TrainingEligibilityService eligibilityService;
    private final TrainingReminderSender reminderSender;
    private final Clock clock;

    private final int daysBeforeDueThreshold;

    public TrainingReminderService(TrainingRepository trainingRepository,
                                   EmployeeService employeeService,
                                   TrainingEligibilityService eligibilityService,
                                   TrainingReminderSender reminderSender,
                                   Clock clock,
                                   int daysBeforeDueThreshold) {
        this.trainingRepository = trainingRepository;
        this.employeeService = employeeService;
        this.eligibilityService = eligibilityService;
        this.reminderSender = reminderSender;
        this.clock = clock;
        this.daysBeforeDueThreshold = daysBeforeDueThreshold;
    }

    public List<TrainingReminder> generateReminders() {
        LocalDate today = clock.today();
        List<TrainingReminder> result = new ArrayList<>();

        List<Training> trainings = trainingRepository.findAll();
        List<Employee> employees = employeeService.getAllEmployees();

        for (Training training : trainings) {
            long daysUntilDue = ChronoUnit.DAYS.between(today, training.getDueDate());
            if (daysUntilDue < 0 || daysUntilDue > daysBeforeDueThreshold) {
                continue;
            }
            for (Employee employee : employees) {
                if (eligibilityService.isTrainingRequiredForEmployee(training, employee)) {
                    result.add(new TrainingReminder(employee, training, daysUntilDue));
                }
            }
        }
        return result;
    }

    public void sendReminders() {
        for (TrainingReminder reminder : generateReminders()) {
            reminderSender.sendReminder(reminder);
        }
    }
}

