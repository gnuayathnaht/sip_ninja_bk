package org.example.sip_bk.service;

import lombok.AllArgsConstructor;
import org.example.sip_bk.dao.EmployeeDao;
import org.example.sip_bk.dto.EmployeeRequest;
import org.example.sip_bk.entity.Employee;
import org.example.sip_bk.entity.Role;
import org.example.sip_bk.entity.Team;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final RoleService roleService;
    private final TeamService teamService;
    private final PasswordEncoder passwordEncoder;

    public Employee findById(Long id) {
        return employeeDao.findById(id).orElse(null);
    }

    public List<Employee> findByRoleId(Long roleId) {
        return employeeDao.findByRoleId(roleId);
    }

    public List<Employee> findByTeamId(Long teamId) {
        return employeeDao.findByTeamId(teamId);
    }

    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    public List<Employee> findByNameContainingIgnoreCase(String name) {
        return employeeDao.findByNameContainingIgnoreCase(name);
    }

    public Employee findByName(String name) {
        return employeeDao.findByName(name)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void save(EmployeeRequest empRequest) {

        Set<Role> roles = new HashSet<>();
        Set<Team> teams = new HashSet<>();
        if (empRequest.getRoleId() != null) {
            roles.add(roleService.getRoleByid(Long.valueOf(empRequest.getRoleId())));
        }

        if (empRequest.getTeamId() != null && !empRequest.getTeamId().isEmpty()) {
            teams.add(teamService.findTeamById(Long.valueOf(empRequest.getTeamId())));
        } else {
            if (!roles.isEmpty()) {
                Role role  = roles.iterator().next();
                System.out.println("role name" + role.getName());
                if (role.getName().equalsIgnoreCase("Team Lead") ||  role.getName().equalsIgnoreCase("Design Team Lead")) {
                    Team team = new Team();
                    team.setName(empRequest.getName());
                    Team savedTeam = teamService.save(team);
                    teams.add(savedTeam);
                }
            }
        }

        Employee employee = new Employee();
        employee.setName(empRequest.getName());

        String encodedPass = passwordEncoder.encode(empRequest.getPassword());

        employee.setPassword(encodedPass);
        employee.setEmail(empRequest.getEmail());
        employee.setPhoneNo(empRequest.getPhone());
        employee.setRoles(roles);
        employee.setTeams(teams);
        employee.setAddress(empRequest.getAddress());
        employee.setImageName(empRequest.getImageName());
        employee.setStatus(empRequest.isStatus());

        employeeDao.save(employee);
    }

    public void update(EmployeeRequest empRequest) {
        Employee employee = employeeDao
                .findById(empRequest.getId()).orElseThrow(() -> new RuntimeException("employee not found"));
        System.out.println("update employee " + employee);

        if (employee != null) {
            employee.setName(empRequest.getName());
            employee.setEmail(empRequest.getEmail());
            employee.setPhoneNo(empRequest.getPhone());
            employee.setAddress(empRequest.getAddress());
            employee.setStatus(empRequest.isStatus());
            employeeDao.save(employee);
        }
    }

    public void deleteById(Long id) {
        employeeDao.deleteById(id);
    }
}
