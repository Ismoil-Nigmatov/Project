package com.example.project.service;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.PasswordDTO;
import com.example.project.dto.UpdateUserDTO;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:35 PM on 11/5/2022
 * @project Project
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ApiResponse getAll() {
        List<User> all = userRepository.findAll();
        return ApiResponse.builder().data(all).success(true).build();
    }

    public ApiResponse photo(String email, MultipartFile multipartFile) throws IOException {
        Optional<User> byId = userRepository.findByEmail(email);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setPhoto(multipartFile.getBytes());
            userRepository.save(user);
            return ApiResponse.builder().success(true).message("Uploaded").build();
        }
        return ApiResponse.builder().success(false).message("Failed! User Not Found").build();
    }

    public ApiResponse getOne(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
        return ApiResponse.builder().success(true).data(user).build();
    }

    public ApiResponse updatePassword(String email, PasswordDTO passwordDTO) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (passwordEncoder.matches(user.getPassword(),passwordEncoder.encode(passwordDTO.getOldPassword()))){
            if (passwordDTO.getPassword().equals(passwordDTO.getConfirmedPassword())){
                user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
                userRepository.save(user);
                return ApiResponse.builder().success(true).message("Edited!").build();
            }else return ApiResponse.builder().success(false).message("Passwords are not the same").build();
        }else return ApiResponse.builder().message("Invalid Password").success(false).build();
    }

    public ApiResponse<?> updateData(String email, UpdateUserDTO updateUserDTO) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            User user = byEmail.get();

            if (Objects.nonNull(updateUserDTO.getFirstName())) user.setFirstName(updateUserDTO.getFirstName());
            if (Objects.nonNull(updateUserDTO.getLastName())) user.setLastName(updateUserDTO.getLastName());
            if (Objects.nonNull(updateUserDTO.getEmail())) user.setEmail(updateUserDTO.getEmail());
            if (Objects.nonNull(updateUserDTO.getPhone())) user.setPhoneNumber(updateUserDTO.getPhone());

            userRepository.save(user);
            return ApiResponse.builder().success(true).message("EDITED!").build();
        }
        return ApiResponse.builder().success(false).build();
    }
}
