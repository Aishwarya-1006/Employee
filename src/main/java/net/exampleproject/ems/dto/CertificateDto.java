package net.exampleproject.ems.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.exampleproject.ems.entity.Employee;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {

    private Long cid;
    private String cname;

    @JsonManagedReference
    private List<Employee> employees;

}
