package net.exampleproject.ems.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.exampleproject.ems.entity.Employee;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long addid;
    private String street;
    private String city;
    private String state;
    private Long zipCode;

    //to show emp details when calling address
    @JsonManagedReference
    private Employee employee;


}
