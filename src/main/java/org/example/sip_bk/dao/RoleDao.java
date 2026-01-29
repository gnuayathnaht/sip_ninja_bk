package org.example.sip_bk.dao;

import org.example.sip_bk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    public Role getRoleByName(String name);
}
