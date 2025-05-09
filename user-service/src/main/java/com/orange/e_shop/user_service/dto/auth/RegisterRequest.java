package com.orange.e_shop.user_service.dto.auth;

import com.orange.e_shop.user_service.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private Role role;

}
