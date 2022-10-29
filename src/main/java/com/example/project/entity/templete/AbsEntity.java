package com.example.project.entity.templete;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:56 PM on 10/6/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public class AbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean deleted;
    private boolean status=true;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;
    @CreatedBy
    private Long createdBy;
    @LastModifiedBy
    private Long updatedBy;

    public AbsEntity(Long id, boolean deleted, boolean status) {

    }
}
