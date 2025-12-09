package org.example.notification;

import java.util.List;

public class NotificationService {
    private final CertificateRepository certificateRepository;
    private final EmailService emailService;

    public NotificationService(CertificateRepository certificateRepository,
                              EmailService emailService) {
        this.certificateRepository = certificateRepository;
        this.emailService = emailService;
    }

    public void notifyAboutExpiringCertificates() {
        List<Certificate> expiringCertificates = certificateRepository.findExpiringCertificates();

        for (Certificate certificate : expiringCertificates) {
            String body = String.format(
                "Twój certyfikat '%s' wygasa w dniu: %s. Zapisz się na szkolenie odświeżające.",
                certificate.getTrainingName(),
                certificate.getExpirationDate()
            );

            Email email = new Email(
                certificate.getEmployeeId() + "@company.com",
                "Przypomnienie o wygasającym certyfikacie",
                body
            );

            emailService.send(email);
        }
    }
}

