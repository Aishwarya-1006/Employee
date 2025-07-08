package net.exampleproject.ems.mapper;

import net.exampleproject.ems.dto.EmployeeDto;
import net.exampleproject.ems.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getEmpid(),
                employee.getEmpname(),
                employee.getPhno(),
                employee.getEmail(),
                employee.getPassword()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getEmpid(),
                employeeDto.getEmpname(),
                employeeDto.getPhno(),
                employeeDto.getEmail(),
                employeeDto.getPassword()
        );
    }
    public static void updateEmployeeFromDto(Employee employee, EmployeeDto employeeDto){
        employee.setEmpname(employeeDto.getEmpname());
        employee.setPhno(employeeDto.getPhno());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
    }
}
