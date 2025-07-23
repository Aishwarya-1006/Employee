package net.exampleproject.ems.service.impl;

import lombok.AllArgsConstructor;
import net.exampleproject.ems.dto.DepartmentDto;
import net.exampleproject.ems.entity.Department;
import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.mapper.DepartmentMapper;
import net.exampleproject.ems.repository.DepartmentRepo;
import net.exampleproject.ems.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepo.findAll()
                .stream()
                .map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long deptId) {
        Department dept = departmentRepo.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + deptId));
        return DepartmentMapper.mapToDepartmentDto(dept);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        // Optional: prevent duplicate department creation
        if (departmentRepo.findByDeptnameIgnoreCase(dto.getDeptname()).isPresent()) {
            throw new RuntimeException("Department already exists with name: " + dto.getDeptname());
        }

        Department dept = DepartmentMapper.mapToDepartment(dto);
        Department savedDept = departmentRepo.save(dept);
        return DepartmentMapper.mapToDepartmentDto(savedDept);
    }

    @Override
    public void deleteDepartment(Long deptId) {
        Department dept = departmentRepo.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + deptId));

        if (dept.getEmployees() != null && !dept.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department with assigned employees.");
        }

        departmentRepo.deleteById(deptId);
    }


    @Override
    public DepartmentDto updateDepartmentName(Long deptId, String newDeptName) {
        // Find department by its ID
        Department dept = departmentRepo.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + deptId));

        // Optional: Check if the new department name already exists
        if (departmentRepo.findByDeptnameIgnoreCase(newDeptName).isPresent()) {
            throw new RuntimeException("Department already exists with name: " + newDeptName);
        }

        // Update the department's name
        dept.setDeptname(newDeptName);

        // Save the updated department
        Department updatedDept = departmentRepo.save(dept);

        // Return the updated DepartmentDto
        return DepartmentMapper.mapToDepartmentDto(updatedDept);
    }


}
