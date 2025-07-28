package net.exampleproject.ems.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

 private Long empid;
 private String empname;
 private Long phno;
 private String email;
 private String password;

 //Department
 private Long deptid;
 private String deptname;

 //Address
 private Long addid;
 private String street;
 private String city;
 private String state;
 private Long zipCode;

 // List of certificates
 private List<BasicCertificateDto> certificates;

 
}