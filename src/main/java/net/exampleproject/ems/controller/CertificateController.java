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

    public final String CERTIFICATE_CREATED = "Certificate created successfully";
    public final String CERTIFICATE_FETCHED = "Certificate fetched successfully";
    public final String ALL_CERTIFICATE_FETCHED = "All certificates fetched successfully";
    public final String CERTIFICATE_UPDATED = "Certificate updated successfully";
    public final String CERTIFICATE_DELETED = "Certificate deleted successfully";

    // ✅ Create Certificate
    @PostMapping
    public ResponseEntity<CustomResponse<CertificateDto>> createCertificate(@RequestBody CertificateDto certificateDTO) {
        CertificateDto created = certificateService.createCertificate(certificateDTO);
        HttpStatus status = HttpStatus.CREATED;
        CustomResponse<CertificateDto> response = new CustomResponse<>(
                CERTIFICATE_CREATED,
                created,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Get All Certificates
    @GetMapping
    public ResponseEntity<CustomResponse<List<CertificateDto>>> getAllCertificates() {
        List<CertificateDto> certificates = certificateService.getAllCertificates();
        HttpStatus status = HttpStatus.OK;
        CustomResponse<List<CertificateDto>> response = new CustomResponse<>(
                ALL_CERTIFICATE_FETCHED,
                certificates,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Get Certificate by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<CertificateDto>> getCertificateById(@PathVariable("id") Long certificateId) {
        CertificateDto certificate = certificateService.getCertificateById(certificateId);
        HttpStatus status = HttpStatus.OK;
        CustomResponse<CertificateDto> response = new CustomResponse<>(
                CERTIFICATE_FETCHED,
                certificate,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Update Certificate
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<CertificateDto>> updateCertificate(
            @PathVariable("id") Long certificateId,
            @RequestBody CertificateDto certificateDTO) {
        CertificateDto updated = certificateService.updateCertificate(certificateId, certificateDTO);
        HttpStatus status = HttpStatus.OK;
        CustomResponse<CertificateDto> response = new CustomResponse<>(
                CERTIFICATE_UPDATED,
                updated,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ✅ Delete Certificate
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteCertificate(@PathVariable("id") Long certificateId) {
        certificateService.deleteCertificate(certificateId);
        HttpStatus status = HttpStatus.OK;
        CustomResponse<Void> response = new CustomResponse<>(
                CERTIFICATE_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }
}
