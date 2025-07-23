package net.exampleproject.ems.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long addid;

    @Column(nullable = false)
    String street;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column
    Long zipCode;

    @OneToOne(mappedBy = "address")
    private Employee employee;
}
