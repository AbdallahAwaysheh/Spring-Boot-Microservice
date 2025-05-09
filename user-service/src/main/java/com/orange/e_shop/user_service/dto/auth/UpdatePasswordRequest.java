package com.orange.e_shop.user_service.dto.auth;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String email;
    private String resetToken;
    private String newPassword;
}
