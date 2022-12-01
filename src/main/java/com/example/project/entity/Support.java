package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:14 PM on 12/1/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String description;
}
