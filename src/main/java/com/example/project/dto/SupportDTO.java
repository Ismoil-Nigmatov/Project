package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 4:23 PM on 12/6/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupportDTO {
    private String fullName;

    private String email;

    private String phone;

    private String description;
}
