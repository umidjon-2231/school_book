package com.project.school_book.repository;

import com.project.school_book.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}