package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.PasswordDTO;
import com.example.project.dto.UpdateUserDTO;
import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:34 PM on 11/5/2022
 * @project Project
 */
@RestController
@RequestMapping("/u/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"},maxAge = 4800,allowCredentials = "false")
public class UserController {

    private final UserService userService;

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
       ApiResponse<?> response = userService.getAll();
       return ResponseEntity.ok(response);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN','USER')")
    @PostMapping("/profile/photo/{email}")
    public ResponseEntity<?> profilePhoto(@PathVariable String email,@RequestPart MultipartFile multipartFile) throws IOException {
        ApiResponse<?> response= userService.photo(email,multipartFile);
        return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

//    @PreAuthorize(value = "hasAnyAuthority('ADMIN','USER')")
//    @GetMapping("/{email}")
//    public ResponseEntity<?> getUser(@PathVariable String email){
//        ApiResponse<?> response=userService.getOne(email);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/data/{email}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> editData(@PathVariable String email, @RequestBody UpdateUserDTO updateUserDTO){
        ApiResponse<?> response=userService.updateData(email,updateUserDTO);
        return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

    @PutMapping("/password/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> editPassword(@PathVariable String email, @RequestBody PasswordDTO passwordDTO){
        ApiResponse<?> response=userService.updatePassword(email,passwordDTO);
        return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }
}
