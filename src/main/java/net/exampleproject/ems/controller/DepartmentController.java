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

    private final String DEPARTMENT_CREATED = "Department created successfully";
    private final String DEPARTMENT_FETCHED = "Department fetched successfully";
    private final String ALL_DEPARTMENT_FETCHED = "All department fetched successfully";
    private final String DEPARTMENT_UPDATED = "Department updated successfully";
    private final String DEPARTMENT_DELETED = "Department deleted successfully";

    @PostMapping
    public ResponseEntity<CustomResponse> createDepartment(@RequestBody DepartmentDto dto) {
        DepartmentDto created = departmentService.createDepartment(dto);
        HttpStatus status = HttpStatus.CREATED;
        CustomResponse response = new CustomResponse(
                DEPARTMENT_CREATED,
                created,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAllDepartments() {

        List<DepartmentDto> departments = departmentService.getAllDepartments();
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                ALL_DEPARTMENT_FETCHED,
                departments,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getDepartmentById(@PathVariable("id") Long id) {
        DepartmentDto department = departmentService.getDepartmentById(id);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                DEPARTMENT_FETCHED,
                department,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                DEPARTMENT_DELETED,
                null,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);
    }

    @PutMapping("/{deptId}/name")
    public ResponseEntity<CustomResponse> updateDeptName(
            @PathVariable Long deptId,
            @RequestParam String newDeptName) {
        DepartmentDto updatedDept = departmentService.updateDepartmentName(deptId, newDeptName);
        HttpStatus status = HttpStatus.OK;
        CustomResponse response = new CustomResponse(
                DEPARTMENT_UPDATED,
                updatedDept,
                true,
                status.value() + " " + status.getReasonPhrase()
        );
        return new ResponseEntity<>(response, status);

    }


}
