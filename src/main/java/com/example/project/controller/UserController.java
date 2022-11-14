package com.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:34 PM on 11/5/2022
 * @project Project
 */
@RestController
@RequestMapping("/u/user")
@RequiredArgsConstructor
public class UserController {
//
//    private final UserService userService;
//
//    @PreAuthorize(value = "hasAuthority('ADMIN')")
//    @GetMapping
//    public ResponseEntity<?> getAllUsers(){
//       ApiResponse response = userService.getAll();
//       return ResponseEntity.ok(response);
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('ADMIN','USER')")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Long id ,@Valid @RequestBody UserDTO userDto){
//       ApiResponse response = userService.update(id,userDto);
//       return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
//    }
//
//    @PreAuthorize(value = "hasAuthority('ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id){
//       ApiResponse response = userService.delete(id);
//       return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.FORBIDDEN).body(response);
//    }
}
