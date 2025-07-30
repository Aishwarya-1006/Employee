package net.exampleproject.ems.service;

import net.exampleproject.ems.dto.BasicCertificateDto;
import net.exampleproject.ems.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto addCertificatesToEmployee(Long empId, List<BasicCertificateDto> certificateDtos);

    EmployeeDto getEmployeeById(Long employeeId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);

    void deleteEmployee(Long employeeId);
}