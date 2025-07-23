package net.exampleproject.ems.repository;

import net.exampleproject.ems.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepo extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCnameIgnoreCase(String certificateName);
}
