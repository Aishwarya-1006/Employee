package net.exampleproject.ems.service;

import net.exampleproject.ems.dto.CertificateDto;

import java.util.List;

public interface CertificateService {

    CertificateDto createCertificate(CertificateDto dto);

    List<CertificateDto> getAllCertificates();

    CertificateDto getCertificateById(Long cid);

    CertificateDto updateCertificate(Long cid, CertificateDto dto);

    void addCertificateToEmployee(Long empId, CertificateDto certificateDto);

    void deleteCertificate(Long cid);
}
