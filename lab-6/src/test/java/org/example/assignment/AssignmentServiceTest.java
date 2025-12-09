package org.example.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private CalendarService calendarService;

    @Mock
    private CompetenceRepository competenceRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void shouldAssignTaskWhenEmployeeIsAvailableAndHasCompetence() {
        // Arrange
        String employeeId = "EMP001";
        String taskId = "TASK123";
        String taskType = "DEVELOPMENT";

        when(calendarService.isEmployeeAvailable(employeeId)).thenReturn(true);
        when(competenceRepository.hasRequiredCompetence(employeeId, taskType)).thenReturn(true);

        // Act
        boolean result = assignmentService.assignTask(employeeId, taskId, taskType);

        // Assert
        assertTrue(result);
        verify(assignmentRepository, times(1)).save(employeeId, taskId);
    }

    @Test
    void shouldNotAssignTaskWhenEmployeeIsNotAvailable() {
        // Arrange
        String employeeId = "EMP002";
        String taskId = "TASK456";
        String taskType = "TESTING";

        when(calendarService.isEmployeeAvailable(employeeId)).thenReturn(false);

        // Act
        boolean result = assignmentService.assignTask(employeeId, taskId, taskType);

        // Assert
        assertFalse(result);
        verify(assignmentRepository, never()).save(anyString(), anyString());
    }

    @Test
    void shouldNotAssignTaskWhenEmployeeLacksCompetence() {
        // Arrange
        String employeeId = "EMP003";
        String taskId = "TASK789";
        String taskType = "ARCHITECTURE";

        when(calendarService.isEmployeeAvailable(employeeId)).thenReturn(true);
        when(competenceRepository.hasRequiredCompetence(employeeId, taskType)).thenReturn(false);

        // Act
        boolean result = assignmentService.assignTask(employeeId, taskId, taskType);

        // Assert
        assertFalse(result);
        verify(assignmentRepository, never()).save(anyString(), anyString());
    }
}

