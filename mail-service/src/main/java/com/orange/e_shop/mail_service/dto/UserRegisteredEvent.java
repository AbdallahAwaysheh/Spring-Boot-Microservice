package com.orange.e_shop.mail_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRegisteredEvent {
    private String email;
    private String name;
}
