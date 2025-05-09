package com.orange.e_shop.user_service.service.admin;

import com.orange.e_shop.user_service.dto.admindto.BlockUserRequest;
import com.orange.e_shop.user_service.model.User;
import com.orange.e_shop.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String blockUser(BlockUserRequest request) {

        String adminUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User admin = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!passwordEncoder.matches(request.getAdminPassword(), admin.getPassword()))
            throw new RuntimeException("Invalid admin password");

        String username = request.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBlocked(request.isBlocked());

        userRepository.save(user);

        if(!request.isBlocked())
            return "User unblocked successfully";

        return "User blocked successfully";
    }
}
