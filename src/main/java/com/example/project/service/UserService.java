package com.example.project.service;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public ApiResponse getAll() {
        List<User> all = userRepository.findAll();
        return ApiResponse.builder().data(all).success(true).build();
    }

    public ApiResponse update(Long id,UserDto userDto) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            User user = byId.get();

            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            if (Objects.nonNull(userDto.getCompanyName()))user.setCompanyName(userDto.getCompanyName());
            userRepository.save(user);
            return ApiResponse.builder().message("Updated").success(true).build();
        }return ApiResponse.builder().success(false).message("User Not Found").build();
    }

    public ApiResponse delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.deleteById(id);
            return ApiResponse.builder().success(true).message("Deleted").build();
        }return ApiResponse.builder().success(false).message("User Not Found").build();
    }
}
