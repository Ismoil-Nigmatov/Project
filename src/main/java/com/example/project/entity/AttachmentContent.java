package com.example.project.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:44 PM on 11/15/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType; //.xls .doc

    private long size;

    private byte[] bytes;

}
