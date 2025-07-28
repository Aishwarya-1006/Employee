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

    private final String EMPLOYEE_CREATED = "Employee created successfully";
    private final String EMPLOYEE_FETCHED = "Employee fetched successfully";
    private final String ALL_EMPLOYEES_FETCHED = "All employees are fetched successfully";
    private final String EMPLOYEE_UPDATED = "Employee updated successfully";
    private final String EMPLOYEE_DELETED = "Employee deleted successfully";

    private final String CERTIFICATE_ADDED_TO_EMPLOYEE = "Certificate added to employee successfully";


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping
    public ResponseEntity<CustomResponse> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);

        HttpStatus status = HttpStatus.CREATED;

        CustomResponse response = new CustomResponse(
                EMPLOYEE_CREATED,
                savedEmployee,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }


    @PostMapping("/add-certificate")
    public ResponseEntity<CustomResponse> addCertificatesToEmployee(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<BasicCertificateDto> certificateDtos) {

        String token = authHeader.substring(7); // Remove "Bearer "
        Long empId = jwtUtil.extractEmpId(token);

        EmployeeDto updatedEmployee = employeeService.addCertificatesToEmployee(empId, certificateDtos);

        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                CERTIFICATE_ADDED_TO_EMPLOYEE,
                updatedEmployee,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return ResponseEntity.ok(updatedEmployee);
    }


    @GetMapping("/getemp")
    public ResponseEntity<CustomResponse> getEmployeeById(@RequestHeader("Authorization") String authHeader) {
        //EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        //return ResponseEntity.ok(employeeDto);
        String token = authHeader.substring(7);
        Long empId = jwtUtil.extractEmpId(token);

        EmployeeDto employee = employeeService.getEmployeeById(empId);

        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                EMPLOYEE_FETCHED,
                employee,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return ResponseEntity.ok(employee);
    }


    @GetMapping
    public ResponseEntity<CustomResponse> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();

        HttpStatus status = HttpStatus.OK;

        CustomResponse response = new CustomResponse(
                ALL_EMPLOYEES_FETCHED,
                employees,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        // return ResponseEntity.ok(employees);
    }


    @PutMapping("/putemp")
    public ResponseEntity<CustomResponse> updateEmployee(@RequestHeader("Authorization") String authHeader,
                                                      @RequestBody EmployeeDto updatedEmployee) {
        String token = authHeader.substring(7);
        Long empId = jwtUtil.extractEmpId(token);

        EmployeeDto employeeDto = employeeService.updateEmployee(empId, updatedEmployee);

        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                EMPLOYEE_UPDATED,
                employeeDto,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return ResponseEntity.ok(employeeDto);
    }


    @DeleteMapping("/delemp")
    public ResponseEntity<CustomResponse> deleteEmployee(@RequestHeader("Authorization")String authHeader) {

        String token = authHeader.substring(7); // Remove "Bearer "
        Long empId = jwtUtil.extractEmpId(token);

        employeeService.deleteEmployee(empId);

        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                EMPLOYEE_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return ResponseEntity.ok("Employee deleted successfully!");
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse> handleResourceNotFound(ResourceNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomResponse response = new CustomResponse(
                ex.getMessage(),
                null,
                false,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponse> handleBadRequest(BadRequestException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomResponse response = new CustomResponse(
                "Validation failed",
                ex.getErrors(),
                false,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleOtherExceptions(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomResponse response = new CustomResponse(
                "Unexpected error: " + ex.getMessage(),
                null,
                false,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

        //return new ResponseEntity<>("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}