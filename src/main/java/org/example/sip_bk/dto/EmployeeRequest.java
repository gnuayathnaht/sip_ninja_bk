package org.example.sip_bk.dto;

import lombok.Data;

@Data
public class EmployeeRequest {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private boolean status;
    private String address;
    private String imageName;
    private String teamId;
    private String roleId;
}
