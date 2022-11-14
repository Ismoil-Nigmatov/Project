package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:41 PM on 11/1/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDTO {
    @Email(message = "Email should be valid!")
    @NotNull
    private String email;
}
