package net.exampleproject.ems.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.exampleproject.ems.entity.Employee;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private Long deptid;
    private String deptname;

    //to show emp dress
    @JsonManagedReference
    private List<Employee> employees;
}
