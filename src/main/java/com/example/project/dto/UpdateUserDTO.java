package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 4:06 PM on 11/19/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
