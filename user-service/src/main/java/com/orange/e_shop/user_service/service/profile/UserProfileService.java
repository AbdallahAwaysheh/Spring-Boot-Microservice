package com.orange.e_shop.user_service.service.profile;

import com.orange.e_shop.user_service.dto.profile.UserDetailsDto;
import com.orange.e_shop.user_service.model.User;
import com.orange.e_shop.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class UserProfileService {

    private final UserRepository userRepository;

    private final String FILE_PATH = "C:\\Users\\lenovo\\Desktop\\Orange E-Shop System\\user-service\\src\\main\\resources\\profile_pictures\\";

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsDto viewProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDetailsDto(user.getUsername(), user.getEmail(), user.getProfilePictureUrl());
    }

    public UserDetailsDto updateProfile(String username, UserDetailsDto userDetailsDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetailsDto.getUserName());
        user.setEmail(userDetailsDto.getEmail());
        user.setProfilePictureUrl(
                userDetailsDto
                        .getProfilePicturePath()!= null ? userDetailsDto
                        .getProfilePicturePath() : user
                        .getProfilePictureUrl());
        userRepository.save(user);
        return new UserDetailsDto(user.getUsername(), user.getEmail(), user.getProfilePictureUrl());
    }

    @Transactional
    public UserDetailsDto uploadProfilePicture(String username, MultipartFile file) throws FileUploadException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileExtension = file.getOriginalFilename()
                                .substring(file.getOriginalFilename()
                                .lastIndexOf("."));

        String newFileName = username + fileExtension;

        user.setProfilePictureUrl(newFileName);

        userRepository.save(user);

        try {
            file.transferTo(new java.io.File(FILE_PATH + newFileName));
        } catch (IOException e) {
            log.error("Failed to upload file to {}: {}",
                     FILE_PATH, e.getMessage());
            throw new FileUploadException("Failed to upload profile picture", e);
        }

        return new UserDetailsDto(user.getUsername(), user.getEmail(), user.getProfilePictureUrl());
    }
}
