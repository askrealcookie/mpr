package org.example.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldSendEmailsForExpiringCertificates() {
        // Arrange
        Certificate cert1 = new Certificate("EMP001", "Java Developer", "2025-06-30");
        Certificate cert2 = new Certificate("EMP002", "AWS Cloud", "2025-07-15");

        when(certificateRepository.findExpiringCertificates())
            .thenReturn(Arrays.asList(cert1, cert2));

        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);

        // Act
        notificationService.notifyAboutExpiringCertificates();

        // Assert
        verify(emailService, times(2)).send(emailCaptor.capture());

        List<Email> capturedEmails = emailCaptor.getAllValues();

        Email firstEmail = capturedEmails.get(0);
        assertEquals("EMP001@company.com", firstEmail.getRecipient());
        assertTrue(firstEmail.getBody().contains("Java Developer"));
        assertTrue(firstEmail.getBody().contains("2025-06-30"));

        Email secondEmail = capturedEmails.get(1);
        assertEquals("EMP002@company.com", secondEmail.getRecipient());
        assertTrue(secondEmail.getBody().contains("AWS Cloud"));
        assertTrue(secondEmail.getBody().contains("2025-07-15"));
    }

    @Test
    void shouldNotSendEmailsWhenNoCertificatesAreExpiring() {
        // Arrange
        when(certificateRepository.findExpiringCertificates())
            .thenReturn(Collections.emptyList());

        // Act
        notificationService.notifyAboutExpiringCertificates();

        // Assert
        verify(emailService, never()).send(any(Email.class));
    }
}

