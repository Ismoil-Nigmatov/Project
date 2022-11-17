package com.example.project.repository;

import com.example.project.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:59 PM on 11/15/2022
 * @project Project
 */
public interface AttachmentRepository extends JpaRepository<AttachmentContent, Long> {
}
