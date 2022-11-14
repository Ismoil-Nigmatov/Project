package com.example.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:35 PM on 11/5/2022
 * @project Project
 */

@Service
@RequiredArgsConstructor
public class UserService {
//    private final UserRepository userRepository;
//
//    public ApiResponse getAll() {
//        List<User> all = userRepository.findAll();
//        return ApiResponse.builder().data(all).success(true).build();
//    }
//
//    public ApiResponse update(Long id,UserDTO userDto) {
//        Optional<User> byId = userRepository.findById(id);
//        if (byId.isPresent()){
//            User user = byId.get();
//
//            user.setFirstName(userDto.getFirstName());
//            user.setLastName(userDto.getLastName());
//            user.setPhoneNumber(userDto.getPhoneNumber());
//            if (Objects.nonNull(userDto.getCompanyName()))user.setCompanyName(userDto.getCompanyName());
//            userRepository.save(user);
//            return ApiResponse.builder().message("Updated").success(true).build();
//        }return ApiResponse.builder().success(false).message("User Not Found").build();
//    }
//
//    public ApiResponse delete(Long id) {
//        Optional<User> byId = userRepository.findById(id);
//        if (byId.isPresent()) {
//            userRepository.deleteById(id);
//            return ApiResponse.builder().success(true).message("Deleted").build();
//        }return ApiResponse.builder().success(false).message("User Not Found").build();
//    }
}
