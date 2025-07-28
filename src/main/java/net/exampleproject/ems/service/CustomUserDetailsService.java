package net.exampleproject.ems.service;

import net.exampleproject.ems.entity.Employee;
import net.exampleproject.ems.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        //fetch user from database
        Employee emp = employeeRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return new User(emp.getEmail(), emp.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USERROLE")));
    }

}
