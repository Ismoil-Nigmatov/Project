package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:50 PM on 10/6/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @NotNull(message = "This field is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "This field is required")
    private String password;
}
