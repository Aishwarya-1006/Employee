package net.exampleproject.ems.mapper;

import net.exampleproject.ems.dto.BasicCertificateDto;
import net.exampleproject.ems.dto.EmployeeDto;
import net.exampleproject.ems.entity.Address;
import net.exampleproject.ems.entity.Certificate;
import net.exampleproject.ems.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        List<BasicCertificateDto> certificateDtos = null;
        if (employee.getCertificates() != null) {
            certificateDtos = employee.getCertificates().stream()
                    .map(cert -> new BasicCertificateDto(cert.getCid(), cert.getCname()))
                    .collect(Collectors.toList());
        }

        return new EmployeeDto(
                employee.getEmpid(),
                employee.getEmpname(),
                employee.getPhno(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getDepartment() != null ? employee.getDepartment().getDeptid() : null,
                employee.getDepartment() != null ? employee.getDepartment().getDeptname() : null,
                employee.getAddress() != null ? employee.getAddress().getAddid() : null,
                employee.getAddress() != null ? employee.getAddress().getStreet() : null,
                employee.getAddress() != null ? employee.getAddress().getCity() : null,
                employee.getAddress() != null ? employee.getAddress().getState() : null,
                employee.getAddress() != null ? employee.getAddress().getZipCode() : null,
                certificateDtos
        );
    }

    public static Employee mapToEmployee(EmployeeDto dto) {
        List<Certificate> certificates = null;
        if (dto.getCertificates() != null) {
            certificates = dto.getCertificates().stream()
                    .map(cert -> {
                        Certificate c = new Certificate();
                        c.setCid(cert.getCid());
                        c.setCname(cert.getCname());
                        return c;
                    })
                    .collect(Collectors.toList());
        }

        // ✅ New Address object from flat DTO fields
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());

        return new Employee(
                dto.getEmpid(),
                dto.getEmpname(),
                dto.getPhno(),
                dto.getEmail(),
                dto.getPassword(),
                null,        // Department will be set separately
                address,     // ✅ Address set here
                certificates
        );
    }

    public static void updateEmployeeFromDto(Employee employee, EmployeeDto dto) {
        employee.setEmpname(dto.getEmpname());
        employee.setPhno(dto.getPhno());
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());

        // ✅ Update Address fields
        Address address = employee.getAddress();
        if (address == null) {
            address = new Address();
        }

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());

        employee.setAddress(address);

        // ✅ Update certificates
        if (dto.getCertificates() != null) {
            List<Certificate> certificates = dto.getCertificates().stream()
                    .map(cert -> {
                        Certificate c = new Certificate();
                        c.setCid(cert.getCid());
                        c.setCname(cert.getCname());
                        return c;
                    })
                    .collect(Collectors.toList());
            employee.setCertificates(certificates);
        }
    }
}
