package net.exampleproject.ems.service;

import net.exampleproject.ems.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto dto);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long deptId);

    void deleteDepartment(Long deptId);

    DepartmentDto updateDepartmentName(Long id, String newdeptname);
}
