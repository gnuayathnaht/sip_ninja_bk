package org.example.sip_bk.rest;

import lombok.AllArgsConstructor;
import org.example.sip_bk.entity.Role;
import org.example.sip_bk.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/roles")
public class RoleRest {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @GetMapping("/name_search")
    public ResponseEntity<Role> getRoleByName(@RequestParam String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return ResponseEntity.ok().build();
    }
}
