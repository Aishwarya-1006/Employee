package net.exampleproject.ems.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
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

}
