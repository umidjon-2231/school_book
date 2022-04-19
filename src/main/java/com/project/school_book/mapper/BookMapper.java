package com.project.school_book.mapper;

import com.project.school_book.dto.BookDto;
import com.project.school_book.dto.BookFrontDto;
import com.project.school_book.entity.Attachment;
import com.project.school_book.entity.Book;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AttachmentMapper.class})
public interface BookMapper {

    @Mapping(source = "file", target = "file", qualifiedByName = "attachment")
    @Mapping(source = "picture", target = "picture", qualifiedByName = "attachment")
    @Mapping(source = "groupId", target = "group.id")
    @SneakyThrows
    Book toEntity(BookDto dto);

    @Mapping(source = "file.id", target = "file.url", qualifiedByName = "urlFile")
    @Mapping(source = "picture.id", target = "picture.url", qualifiedByName = "urlPicture")
    @Mapping(source = "group.classNumber", target = "classNumber")
    @SneakyThrows
    BookFrontDto toDto(Book book);

    @SneakyThrows
    @Named("attachment")
    default Attachment attachment(MultipartFile file){
        return Attachment.builder()
                .name(file.getOriginalFilename())
                .bytes(file.getBytes())
                .size(file.getSize())
                .type(file.getContentType())
                .build();
    }
    @SneakyThrows
    @Named("attachmentEdit")
    default Attachment attachmentEdit(MultipartFile file, Attachment attachment){
        attachment.setBytes(file.getBytes());
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setType(file.getContentType());
        return attachment;
    }
    @Named("urlFile")
    default String generateUrl(Integer id){
        return "/api/book/file/"+id;
    }
    @Named("urlPicture")
    default String generateUrlPicture(Integer id){
        return "/api/book/picture/"+id;
    }

    @SneakyThrows
    List<BookFrontDto> toDto(List<Book> all);

//    @Mapping(source = "file", target = "file", qualifiedByName = "attachmentEdit")
//    @Mapping(source = "picture", target = "picture", qualifiedByName = "attachmentEdit")
    void update(BookDto dto, @MappingTarget Book book)throws IOException;
}
