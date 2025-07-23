package net.exampleproject.ems.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.exampleproject.ems.entity.Employee;

@Getter
@Setter
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
