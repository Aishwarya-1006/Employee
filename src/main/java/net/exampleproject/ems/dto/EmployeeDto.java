package net.exampleproject.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

 private Long empid;
 private String empname;
 private Long phno;
 private String email;
 private String password;
}
