package org.example.sip_bk.rest;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.example.sip_bk.dto.EmployeeRequest;
import org.example.sip_bk.entity.Employee;
import org.example.sip_bk.service.EmployeeService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeRest {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/name_search")
    public ResponseEntity<List<Employee>> findEmpByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findByNameContainingIgnoreCase(name));
    }

    @GetMapping("/role_search")
    public ResponseEntity<List<Employee>> findEmpListByRoleId(@RequestParam String roleId) {
        return ResponseEntity.ok(employeeService.findByRoleId(Long.parseLong(roleId)));
    }

    @GetMapping("/team_search")
    public ResponseEntity<List<Employee>> findEmpListByTeamId(@RequestParam String teamId) {
        return ResponseEntity.ok(employeeService.findByTeamId(Long.parseLong(teamId)));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(
            @RequestPart("employee") EmployeeRequest employee,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        System.out.println("save employee " + employee);
        System.out.println("save image " + image);

        if (image != null && !image.isEmpty()) {
            System.out.println("start image process");
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);

            try {
                Files.createDirectories(path.getParent());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("fileName " + fileName);
            employee.setImageName(fileName);
        }

        employeeService.save(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(
            @RequestPart("employee") EmployeeRequest employee,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        System.out.println("save employee " + employee);
        System.out.println("save image " + image);

        if (image != null && !image.isEmpty()) {
            System.out.println("start image process");
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);

            try {
                Files.createDirectories(path.getParent());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("fileName " + fileName);
            employee.setImageName(fileName);
        }
        employeeService.update(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{empId}")
    public ResponseEntity<Void> delete(@PathVariable Long empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<UrlResource> getImage(@PathVariable String filename) throws IOException {

        Path path = Paths.get("uploads/" + filename);
        UrlResource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/profile_image/{name}")
    public ResponseEntity<UrlResource> getImageByName(@PathVariable String name) throws IOException {

        Employee employee = employeeService.findByName(name);
        System.out.println("image " + employee.getImageName());
        Path path = Paths.get("uploads/" + employee.getImageName());
        UrlResource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
