package org.example.assignment;

public class AssignmentService {
    private final CalendarService calendarService;
    private final CompetenceRepository competenceRepository;
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(CalendarService calendarService,
                            CompetenceRepository competenceRepository,
                            AssignmentRepository assignmentRepository) {
        this.calendarService = calendarService;
        this.competenceRepository = competenceRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public boolean assignTask(String employeeId, String taskId, String taskType) {
        if (!calendarService.isEmployeeAvailable(employeeId)) {
            return false;
        }

        if (!competenceRepository.hasRequiredCompetence(employeeId, taskType)) {
            return false;
        }

        assignmentRepository.save(employeeId, taskId);
        return true;
    }
}

