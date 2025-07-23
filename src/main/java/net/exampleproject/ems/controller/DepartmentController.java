package net.exampleproject.ems.controller;

import lombok.AllArgsConstructor;
import net.exampleproject.ems.dto.DepartmentDto;
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

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto dto) {
        DepartmentDto created = departmentService.createDepartment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully.");
    }

    @PutMapping("/{deptId}/name")
    public ResponseEntity<DepartmentDto> updateDeptName(
            @PathVariable Long deptId,
            @RequestParam String newDeptName) {
        DepartmentDto updatedDept = departmentService.updateDepartmentName(deptId, newDeptName);
        return ResponseEntity.ok(updatedDept);
    }


}
