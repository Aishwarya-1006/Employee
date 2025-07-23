package net.exampleproject.ems.service.impl;

import net.exampleproject.ems.dto.CertificateDto;
import net.exampleproject.ems.entity.Certificate;
import net.exampleproject.ems.entity.Employee;
import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.mapper.CertificateMapper;
import net.exampleproject.ems.repository.CertificateRepo;
import net.exampleproject.ems.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepo certificateRepo;

    @Autowired
    public CertificateServiceImpl(CertificateRepo certificateRepo){
        this.certificateRepo = certificateRepo;
    }

    @Override
    public CertificateDto createCertificate(CertificateDto dto){
        Certificate certificate = CertificateMapper.mapToCertificate(dto);
        Certificate saved = certificateRepo.save(certificate);
        return CertificateMapper.mapToCertificateDto(saved);
    }

    @Override
    public List<CertificateDto> getAllCertificates() {
        return certificateRepo.findAll()
                .stream()
                .map(CertificateMapper::mapToCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDto getCertificateById(Long cid){
        Certificate certificate = certificateRepo.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("The certificate with given ID is not found"));
        return CertificateMapper.mapToCertificateDto(certificate);
    }

    @Override
    public CertificateDto updateCertificate(Long cid, CertificateDto dto){
        Certificate certificate = certificateRepo.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("The certificate is not found with ID: " + cid));
        certificate.setCname(dto.getCname());
        Certificate updated = certificateRepo.save(certificate);
        return CertificateMapper.mapToCertificateDto(updated);
    }

    // ✅ FIXED METHOD
    @Override
    @Transactional
    public void deleteCertificate(Long cid) {
        Certificate certificate = certificateRepo.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate does not exist with ID: " + cid));

        // Remove this certificate from all associated employees
        for (Employee employee : certificate.getEmployees()) {
            employee.getCertificates().remove(certificate);
        }

        // Then delete the certificate
        certificateRepo.delete(certificate);
    }
}
