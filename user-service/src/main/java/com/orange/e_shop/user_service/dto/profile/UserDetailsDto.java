package com.orange.e_shop.user_service.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String userName;
    private String email;
    private String profilePicturePath;
}
