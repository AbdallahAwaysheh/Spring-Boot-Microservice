package com.orange.e_shop.user_service.dto.auth;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
}
