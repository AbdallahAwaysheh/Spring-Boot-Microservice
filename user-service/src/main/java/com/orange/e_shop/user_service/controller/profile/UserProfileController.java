package com.orange.e_shop.user_service.controller.profile;

import com.orange.e_shop.user_service.dto.profile.UserDetailsDto;
import com.orange.e_shop.user_service.service.profile.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor // Lombok for constructor injection
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public UserDetailsDto viewProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userProfileService.viewProfile(username);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public UserDetailsDto updateProfile(@RequestBody UserDetailsDto userDetailsDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userProfileService.updateProfile(username, userDetailsDto);
    }

    @PostMapping("/upload-profile-picture")
    @PreAuthorize("isAuthenticated()")
    public UserDetailsDto uploadProfilePicture(@RequestParam("file") MultipartFile file) throws FileUploadException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userProfileService.uploadProfilePicture(username, file);
    }
}
