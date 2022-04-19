package com.project.school_book.dto;

import com.project.school_book.entity.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFrontDto {
    private String name;
    private Language language;
    private Integer classNumber;
    private AttachmentDto file, picture;
    private int year;
    private List<String> authors;
}
