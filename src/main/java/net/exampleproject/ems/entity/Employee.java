package net.exampleproject.ems.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.exampleproject.ems.dto.AddressDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long empid;

    @Column(name="emp_name", nullable = false)
    private String empname;

    @Column(name="phone_number", nullable = false)
    private Long phno;

    @Column(name="email_id",nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;


    //ManyToOne mapping to Department
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deptid")
    @JsonBackReference(value = "employee-department") // Unique name
    private Department department;


    //OneToOne mapping to Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonBackReference(value = "employee-address")
    private Address address;


    //ManyToMany mapping to certificate
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( name = "employee_certificate", joinColumns = @JoinColumn(name = "empid"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))

    @JsonBackReference(value = "employee-certificates")
    private List<Certificate> certificates;

}