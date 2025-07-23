package net.exampleproject.ems.repository;

import net.exampleproject.ems.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Optional<Department> findByDeptnameIgnoreCase(String newdeptname);
}
