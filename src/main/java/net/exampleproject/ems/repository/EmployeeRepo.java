package net.exampleproject.ems.repository;

import net.exampleproject.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);
}

