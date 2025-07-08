package net.exampleproject.ems.service.impl;

import net.exampleproject.ems.dto.EmployeeDto;
import net.exampleproject.ems.entity.Employee;
import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.exception.EmployeeValidator;
import net.exampleproject.ems.mapper.EmployeeMapper;
import net.exampleproject.ems.repository.EmployeeRepo;
import net.exampleproject.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeValidator.validate(employeeDto, employeeRepository, false, null);
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
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

        EmployeeMapper.updateEmployeeFromDto(employee, updatedEmployee);

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }
}
