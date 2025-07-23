package net.exampleproject.ems.repository;

import net.exampleproject.ems.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
