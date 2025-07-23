package net.exampleproject.ems.service.impl;

import net.exampleproject.ems.dto.EmployeeDto;
import net.exampleproject.ems.entity.Address;
import net.exampleproject.ems.entity.Certificate;
import net.exampleproject.ems.entity.Department;
import net.exampleproject.ems.entity.Employee;
import net.exampleproject.ems.exception.EmployeeValidator;
import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.mapper.EmployeeMapper;
import net.exampleproject.ems.repository.CertificateRepo;
import net.exampleproject.ems.repository.DepartmentRepo;
import net.exampleproject.ems.repository.EmployeeRepo;
import net.exampleproject.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepository;
    private final DepartmentRepo departmentRepo;
    private final CertificateRepo certificateRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepository, DepartmentRepo departmentRepo, CertificateRepo certificateRepo) {
        this.employeeRepository = employeeRepository;
        this.departmentRepo = departmentRepo;
        this.certificateRepo = certificateRepo;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeValidator.validate(employeeDto, employeeRepository, false, null);

        // Get or create Department
        Department dept = departmentRepo.findByDeptnameIgnoreCase(employeeDto.getDeptname())
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setDeptname(employeeDto.getDeptname());
                    return departmentRepo.save(newDept);
                });

        // Map certificates
        List<Certificate> certificates = employeeDto.getCertificates().stream()
                .map(certDto -> certificateRepo.findByCnameIgnoreCase(certDto.getCname())
                        .orElseGet(() -> {
                            Certificate newCert = new Certificate();
                            newCert.setCname(certDto.getCname());
                            return certificateRepo.save(newCert);
                        }))
                .collect(Collectors.toList());



        // Map flat address fields
        Address address = new Address();
        address.setStreet(employeeDto.getStreet());
        address.setCity(employeeDto.getCity());
        address.setState(employeeDto.getState());
        address.setZipCode(employeeDto.getZipCode());

        // Map EmployeeDto to Employee
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        employee.setDepartment(dept);
        employee.setCertificates(certificates);
        employee.setAddress(address);

        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));

        EmployeeValidator.validate(updatedEmployee, employeeRepository, true, employeeId);

        // Update core fields
        EmployeeMapper.updateEmployeeFromDto(employee, updatedEmployee);

        // Update certificates
        List<Certificate> updatedCerts = updatedEmployee.getCertificates().stream()
                .map(certDto -> certificateRepo.findByCnameIgnoreCase(certDto.getCname())
                        .orElseGet(() -> {
                            Certificate newCert = new Certificate();
                            newCert.setCname(certDto.getCname());
                            return certificateRepo.save(newCert);
                        }))
                .collect(Collectors.toList());
        employee.setCertificates(updatedCerts);

        // Update flat address fields
        Address address = new Address();
        address.setStreet(updatedEmployee.getStreet());
        address.setCity(updatedEmployee.getCity());
        address.setState(updatedEmployee.getState());
        address.setZipCode(updatedEmployee.getZipCode());
        employee.setAddress(address);

        // Update or create department
        Department updatedDept = departmentRepo.findByDeptnameIgnoreCase(updatedEmployee.getDeptname())
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setDeptname(updatedEmployee.getDeptname());
                    return departmentRepo.save(newDept);
                });
        employee.setDepartment(updatedDept);

        // Save updated employee
        Employee updated = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updated);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));

        Department dept = employee.getDepartment();
        if (dept != null) {
            dept.getEmployees().remove(employee);
            employee.setDepartment(null);
        }

        for (Certificate cert : employee.getCertificates()) {
            cert.getEmployees().remove(employee);
        }
        employee.getCertificates().clear();

        employeeRepository.deleteById(employeeId);
    }
}
