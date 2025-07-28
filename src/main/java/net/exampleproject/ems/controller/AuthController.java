package net.exampleproject.ems.controller;

import net.exampleproject.ems.entity.Employee;
import net.exampleproject.ems.exception.ResourceNotFoundException;
import net.exampleproject.ems.repository.EmployeeRepo;
import net.exampleproject.ems.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Employee employee){


        //only accesss token
        /*try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(employee.getEmail(), employee.getPassword()));
            UserDetails userdetails = (UserDetails) authentication.getPrincipal();

            Employee employee1 = employeeRepo.findByEmail(employee.getEmail()).orElseThrow(()->new ResourceNotFoundException("Employee not found"));

            String token = jwtUtil.generateToken(userdetails,employee1);

            return ResponseEntity.ok(Map.of("token", token));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid username or password"));

        }*/


        //  LOGIN with access + refresh token
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(employee.getEmail(), employee.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Employee emp = employeeRepo.findByEmail(employee.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            String accessToken = jwtUtil.generateToken(userDetails, emp);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails, emp);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return ResponseEntity.ok(tokens);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    // REFRESH TOKEN endpoint
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        try {
            String username = jwtUtil.extractUsername(refreshToken);
            Long empId = jwtUtil.extractEmpId(refreshToken);

            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Refresh token is expired"));
            }

            Employee emp = employeeRepo.findById(empId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    username, emp.getPassword(), new ArrayList<>());

            String newAccessToken = jwtUtil.generateToken(userDetails, emp);

            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken,
                    "refreshToken", refreshToken  // You can also regenerate a new one if needed
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid refresh token"));
        }
    }
}





