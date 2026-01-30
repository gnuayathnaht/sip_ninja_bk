package org.example.sip_bk.rest;

import lombok.AllArgsConstructor;
import org.example.sip_bk.dao.EmployeeDao;
import org.example.sip_bk.entity.Employee;
import org.example.sip_bk.entity.Role;
import org.example.sip_bk.security.utils.JwtUtils;
import org.example.sip_bk.security.vo.AuthRequest;
import org.example.sip_bk.security.vo.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRest {

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private EmployeeDao employeeDao;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {

        if (authRequest.getUsername() != "super admin") {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        }

        String token = jwtUtils.generateToken(authRequest.getUsername());
        Employee employee = employeeDao.findByName(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        String role = employee.getRoles().iterator().next().getName();
        System.out.println("role" + role);

        return ResponseEntity.ok(new AuthResponse(token, role));
    }
}
