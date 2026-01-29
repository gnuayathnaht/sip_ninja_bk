package org.example.sip_bk.service;

import lombok.AllArgsConstructor;
import org.example.sip_bk.dao.RoleDao;
import org.example.sip_bk.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleDao roleDao;

    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);
    }

    public Role getRoleByid(Long id) {
        return roleDao.findById(id).orElse(null);
    }

    public void save(Role role) {
        roleDao.save(role);
    }
}
