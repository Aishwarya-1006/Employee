package net.exampleproject.ems.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Column(nullable = false, unique = true)
    private String cname;

    @ManyToMany(mappedBy = "certificates")
    private List<Employee> employees;

}
