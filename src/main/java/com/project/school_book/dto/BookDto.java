package com.project.school_book.dto;

import com.project.school_book.entity.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer groupId;
    @Size(min = 1)
    private List<String> authors;
    @Min(1800)
    private int year;
    @NotNull
    private Language language;
    @NotNull
    private MultipartFile file, picture;
}
