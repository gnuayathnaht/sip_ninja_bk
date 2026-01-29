package org.example.sip_bk.dao;

import org.example.sip_bk.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    @Query("""
        SELECT DISTINCT e FROM Employee e JOIN e.roles r WHERE r.id = :roleId
    """)
    List<Employee> findByRoleId(Long roleId);

    @Query("""
        SELECT DISTINCT e FROM Employee e JOIN e.teams t WHERE t.id = :teamId
    """)
    List<Employee> findByTeamId(Long teamId);

    Optional<Employee> findByName(String name);
}
