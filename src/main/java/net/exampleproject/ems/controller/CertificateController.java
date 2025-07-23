package net.exampleproject.ems.controller;

import net.exampleproject.ems.dto.CertificateDto;
import net.exampleproject.ems.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    // Create a Certificate
    @PostMapping
    public ResponseEntity<CertificateDto> createCertificate(@RequestBody CertificateDto certificateDTO) {
        CertificateDto createdCertificate = certificateService.createCertificate(certificateDTO);
        return ResponseEntity.status(201).body(createdCertificate);
    }

    // Get All Certificates
    @GetMapping
    public ResponseEntity<List<CertificateDto>> getAllCertificates() {
        List<CertificateDto> certificates = certificateService.getAllCertificates();
        return ResponseEntity.ok(certificates);
    }

    // Get Certificate by ID
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable("id") Long certificateId) {
        CertificateDto certificate = certificateService.getCertificateById(certificateId);
        return ResponseEntity.ok(certificate);
    }

    // Update Certificate
    @PutMapping("/{id}")
    public ResponseEntity<CertificateDto> updateCertificate(
            @PathVariable("id") Long certificateId,
            @RequestBody CertificateDto certificateDTO) {
        CertificateDto updatedCertificate = certificateService.updateCertificate(certificateId, certificateDTO);
        return ResponseEntity.ok(updatedCertificate);
    }

    // Delete Certificate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable("id") Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        return ResponseEntity.noContent().build();
    }
}

