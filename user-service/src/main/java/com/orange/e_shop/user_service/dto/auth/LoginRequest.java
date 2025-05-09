package com.orange.e_shop.user_service.dto.auth;

import lombok.*;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
