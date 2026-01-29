package org.example.sip_bk.security.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String role;
}
