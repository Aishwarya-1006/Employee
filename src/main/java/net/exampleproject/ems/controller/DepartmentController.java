package net.exampleproject.ems.controller;

import lombok.AllArgsConstructor;
import net.exampleproject.ems.dto.DepartmentDto;
import net.exampleproject.ems.response.CustomResponse;
import net.exampleproject.ems.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public final String DEPARTMENT_CREATED = "Department created successfully";
    public final String DEPARTMENT_FETCHED = "Department fetched successfully";
    public final String ALL_DEPARTMENT_FETCHED = "All departments fetched successfully";
    public final String DEPARTMENT_UPDATED = "Department updated successfully";
    public final String DEPARTMENT_DELETED = "Department deleted successfully";

    // CREATE department
    @PostMapping
    public ResponseEntity<CustomResponse<DepartmentDto>> createDepartment(@RequestBody DepartmentDto dto) {
        DepartmentDto created = departmentService.createDepartment(dto);
        HttpStatus status = HttpStatus.CREATED;

        CustomResponse<DepartmentDto> response = new CustomResponse<>(
                DEPARTMENT_CREATED,
                created,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // GET all departments
    @GetMapping
    public ResponseEntity<CustomResponse<List<DepartmentDto>>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        HttpStatus status = HttpStatus.OK;

        CustomResponse<List<DepartmentDto>> response = new CustomResponse<>(
                ALL_DEPARTMENT_FETCHED,
                departments,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // GET department by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<DepartmentDto>> getDepartmentById(@PathVariable("id") Long id) {
        DepartmentDto department = departmentService.getDepartmentById(id);
        HttpStatus status = HttpStatus.OK;

        CustomResponse<DepartmentDto> response = new CustomResponse<>(
                DEPARTMENT_FETCHED,
                department,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // DELETE department
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        HttpStatus status = HttpStatus.OK;

        CustomResponse<Void> response = new CustomResponse<>(
                DEPARTMENT_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    // UPDATE department name
    @PutMapping("/{deptId}/name")
    public ResponseEntity<CustomResponse<DepartmentDto>> updateDeptName(
            @PathVariable Long deptId,
            @RequestParam String newDeptName) {
        DepartmentDto updatedDept = departmentService.updateDepartmentName(deptId, newDeptName);
        HttpStatus status = HttpStatus.OK;

        CustomResponse<DepartmentDto> response = new CustomResponse<>(
                DEPARTMENT_UPDATED,
                updatedDept,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }
}
