package net.exampleproject.ems.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.exampleproject.ems.entity.Employee;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {

    private Long cid;
    private String cname;

    @JsonManagedReference
    private List<Employee> employees;

}
