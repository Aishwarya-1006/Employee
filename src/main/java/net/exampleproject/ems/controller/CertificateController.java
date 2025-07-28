package net.exampleproject.ems.controller;

import net.exampleproject.ems.dto.CertificateDto;
import net.exampleproject.ems.response.CustomResponse;
import net.exampleproject.ems.security.JwtUtil;
import net.exampleproject.ems.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CertificateService certificateService;

    private final String CERTIFICATE_CREATED = "Certificate created successfully";
    private final String CERTIFICATE_FETCHED = "Certificate fetched successfully";
    private final String ALL_CERTIFICATE_FETCHED = "All certificates fetched successfully";
    private final String CERTIFICATE_UPDATED = "Certificate updated successfully";
    private final String CERTIFICATE_DELETED = "Certificate deleted successfully";

    // Create a Certificate
    @PostMapping
    public ResponseEntity<CustomResponse> createCertificate(@RequestBody CertificateDto certificateDTO) {
        CertificateDto createdCertificate = certificateService.createCertificate(certificateDTO);
        HttpStatus status = HttpStatus.CREATED;
        CustomResponse response = new CustomResponse(
                CERTIFICATE_CREATED,
                createdCertificate,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }



    // Get All Certificates
    @GetMapping
    public ResponseEntity<CustomResponse> getAllCertificates() {
        List<CertificateDto> certificates = certificateService.getAllCertificates();
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                ALL_CERTIFICATE_FETCHED,
                certificates,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // Get Certificate by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getCertificateById(@PathVariable("id") Long certificateId) {
        CertificateDto certificate = certificateService.getCertificateById(certificateId);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                CERTIFICATE_FETCHED,
                certificate,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

    }

    // Update Certificate
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateCertificate(
            @PathVariable("id") Long certificateId,
            @RequestBody CertificateDto certificateDTO) {
        CertificateDto updatedCertificate = certificateService.updateCertificate(certificateId, certificateDTO);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                CERTIFICATE_UPDATED,
                updatedCertificate,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

    }

    // Delete Certificate
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteCertificate(@PathVariable("id") Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                CERTIFICATE_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }
}

