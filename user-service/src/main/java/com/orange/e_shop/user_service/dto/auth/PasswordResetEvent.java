package com.orange.e_shop.user_service.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetEvent implements Serializable {

    private String email;
    private String resetToken;
}
