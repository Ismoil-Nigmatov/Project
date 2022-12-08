package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:56 PM on 10/27/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @Size(min = 3,message = "At least 3 elements.")
    private String firstName;

    @Size(min = 3,message = "At least 3 elements.")
    private String lastName;

    @Email(message = "Email should be valid! ")
    private String email;

    @Size(min = 8,message = "Password should not be less than 8 ")
    private String password;

    @NotNull
    private String secondPassword;

    @NotNull(message = "This field  is required")
    private String phoneNumber;

    private String captcha;

    private String hiddenCaptcha;

    private String realCaptcha;
}
