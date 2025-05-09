package com.orange.e_shop.user_service.controller.admin;

import com.orange.e_shop.user_service.dto.admindto.BlockUserRequest;
import com.orange.e_shop.user_service.model.User;
import com.orange.e_shop.user_service.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PatchMapping("/users/block")
    @PreAuthorize("hasRole('ADMIN')")
    public String blockUser(@RequestBody BlockUserRequest request) {
        return adminService.blockUser(request);
    }

}