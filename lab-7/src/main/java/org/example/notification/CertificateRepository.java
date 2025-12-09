package org.example.notification;

import java.util.List;

public interface CertificateRepository {
    List<Certificate> findExpiringCertificates();
}

