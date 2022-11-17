package com.example.project.service;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.UserDTO;
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

    public ApiResponse update(Long id, UserDTO userDto) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            User user = byId.get();

            if (Objects.nonNull(userDto.getFirstName())) user.setFirstName(userDto.getFirstName());
            if (Objects.nonNull(userDto.getLastName()))user.setLastName(userDto.getLastName());
            if (Objects.nonNull(userDto.getPhoneNumber()))user.setPhoneNumber(userDto.getPhoneNumber());
            if (Objects.nonNull(userDto.getPassword())){
                if (userDto.getPassword().equals(userDto.getSecondPassword())) {
                    if (passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(userDto.getPassword()))){
                        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    }return ApiResponse.builder().success(false).message("Incorrect Password").build();
                }return ApiResponse.builder().success(false).message("Passwords are not the same").build();
            }
            userRepository.save(user);
            return ApiResponse.builder().message("Updated").success(true).build();
        }return ApiResponse.builder().success(false).message("User Not Found").build();
    }

    public ApiResponse photo(Long id, MultipartFile multipartFile) throws IOException {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setPhoto(multipartFile.getBytes());
            userRepository.save(user);
            return ApiResponse.builder().success(true).message("Uploaded").build();
        }
        return ApiResponse.builder().success(false).message("Failed! User Not Found").build();
    }
}
