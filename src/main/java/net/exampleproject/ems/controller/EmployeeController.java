package net.exampleproject.ems.controller;

import lombok.AllArgsConstructor;
import net.exampleproject.ems.dto.BasicCertificateDto;
import net.exampleproject.ems.dto.EmployeeDto;
import net.exampleproject.ems.exception.BadRequestException;
import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.repository.EmployeeRepo;
import net.exampleproject.ems.response.CustomResponse;
import net.exampleproject.ems.security.JwtUtil;
import net.exampleproject.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public final String EMPLOYEE_CREATED = "Employee created successfully";
    public final String EMPLOYEE_FETCHED = "Employee fetched successfully";
    public final String ALL_EMPLOYEES_FETCHED = "All employees are fetched successfully";
    public final String EMPLOYEE_UPDATED = "Employee updated successfully";
    public final String EMPLOYEE_DELETED = "Employee deleted successfully";
    public final String CERTIFICATE_ADDED_TO_EMPLOYEE = "Certificate added to employee successfully";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepo employeeRepo;

    // CREATE employee
    @PostMapping
    public ResponseEntity<CustomResponse<EmployeeDto>> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        HttpStatus status = HttpStatus.CREATED;

        CustomResponse<EmployeeDto> response = new CustomResponse<>(
                EMPLOYEE_CREATED,
                savedEmployee,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // ADD CERTIFICATE to employee
    @PostMapping("/add-certificate")
    public ResponseEntity<CustomResponse<EmployeeDto>> addCertificatesToEmployee(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<BasicCertificateDto> certificateDtos) {

        String token = authHeader.substring(7);
        Long empId = jwtUtil.extractEmpId(token);

        EmployeeDto updatedEmployee = employeeService.addCertificatesToEmployee(empId, certificateDtos);

        HttpStatus status = HttpStatus.OK;
        CustomResponse<EmployeeDto> response = new CustomResponse<>(
                CERTIFICATE_ADDED_TO_EMPLOYEE,
                updatedEmployee,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // GET EMPLOYEE by token
    @GetMapping("/getemp")
    public ResponseEntity<CustomResponse<EmployeeDto>> getEmployeeById(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long empId = jwtUtil.extractEmpId(token);

        EmployeeDto employee = employeeService.getEmployeeById(empId);

        HttpStatus status = HttpStatus.OK;
        CustomResponse<EmployeeDto> response = new CustomResponse<>(
                EMPLOYEE_FETCHED,
                employee,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // GET ALL employees
    @GetMapping
    public ResponseEntity<CustomResponse<List<EmployeeDto>>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();

        HttpStatus status = HttpStatus.OK;
        CustomResponse<List<EmployeeDto>> response = new CustomResponse<>(
                ALL_EMPLOYEES_FETCHED,
                employees,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @PutMapping("/putemp")
    public ResponseEntity<CustomResponse<EmployeeDto>> updateEmployee(@RequestHeader("Authorization") String authHeader,
                                                                      @RequestBody EmployeeDto updatedEmployee) {

        String token = authHeader.substring(7);
        Long empId = jwtUtil.extractEmpId(token);

        EmployeeDto employeeDto = employeeService.updateEmployee(empId, updatedEmployee);

        HttpStatus status = HttpStatus.OK;
        CustomResponse<EmployeeDto> response = new CustomResponse<>(
                EMPLOYEE_UPDATED,
                employeeDto,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/delemp")
    public ResponseEntity<CustomResponse<Void>> deleteEmployee(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long empId = jwtUtil.extractEmpId(token);

        employeeService.deleteEmployee(empId);

        HttpStatus status = HttpStatus.OK;
        CustomResponse<Void> response = new CustomResponse<>(
                EMPLOYEE_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomResponse<Void> response = new CustomResponse<>(
                ex.getMessage(),
                null,
                false,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponse<Object>> handleBadRequest(BadRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomResponse<Object> response = new CustomResponse<>(
                "Validation failed",
                ex.getErrors(),
                false,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Void>> handleOtherExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomResponse<Void> response = new CustomResponse<>(
                "Unexpected error: " + ex.getMessage(),
                null,
                false,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }
}
