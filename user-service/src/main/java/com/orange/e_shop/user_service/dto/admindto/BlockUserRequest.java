package com.orange.e_shop.user_service.dto.admindto;

import lombok.Data;

@Data
public class BlockUserRequest {
    private String username;
    private String adminPassword;
    private boolean blocked;


}
