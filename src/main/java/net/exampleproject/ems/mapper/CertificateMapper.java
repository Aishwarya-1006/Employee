package net.exampleproject.ems.mapper;

import net.exampleproject.ems.dto.CertificateDto;
import net.exampleproject.ems.entity.Certificate;

public class CertificateMapper {

    public static CertificateDto mapToCertificateDto(Certificate certificate) {
        if (certificate == null) return null;
        return new CertificateDto(certificate.getCid(), certificate.getCname(), certificate.getEmployees());
    }

    public static Certificate mapToCertificate(CertificateDto dto) {
        if (dto == null) return null;
        Certificate certificate = new Certificate();
        certificate.setCid(dto.getCid());
        certificate.setCname(dto.getCname());
        certificate.setEmployees(dto.getEmployees());
        return certificate;
    }
}
