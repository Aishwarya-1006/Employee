package net.exampleproject.ems.mapper;

import net.exampleproject.ems.dto.DepartmentDto;
import net.exampleproject.ems.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto mapToDepartmentDto(Department dept) {
        if (dept == null) return null;
        return new DepartmentDto(dept.getDeptid(), dept.getDeptname(), dept.getEmployees());
    }

    public static Department mapToDepartment(DepartmentDto dto) {
        if (dto == null) return null;
        Department dept = new Department();
        dept.setDeptid(dto.getDeptid());
        dept.setDeptname(dto.getDeptname());
        return dept;
    }
}
