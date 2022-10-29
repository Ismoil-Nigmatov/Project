package com.example.project.entity;

import com.example.project.entity.templete.AbsEntity;
import com.example.project.entity.templete.AbsNameEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import java.sql.Timestamp;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 12:09 PM on 10/8/2022
 * @project Project
 */

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Where(clause = "deleted=false")
@SQLDelete(sql = "update role set deleted=true,status=false where id=?")
public class Role extends AbsEntity {
    private String name;

    public Role(Long id, boolean deleted, boolean status,String name) {
        super(id, deleted,status);
        this.name = name;
    }
}
