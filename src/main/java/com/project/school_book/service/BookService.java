package com.project.school_book.service;

import com.project.school_book.dto.ApiResponse;
import com.project.school_book.dto.BookDto;
import com.project.school_book.dto.BookFrontDto;
import com.project.school_book.entity.Book;
import com.project.school_book.mapper.BookMapper;
import com.project.school_book.repository.BookRepository;
import com.project.school_book.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    final BookRepository bookRepository;
    final GroupRepository groupRepository;
    final BookMapper bookMapper;

    public ApiResponse<BookFrontDto> add(BookDto dto){
        if(dto.getFile().isEmpty() || dto.getPicture().isEmpty()){
            return ApiResponse.<BookFrontDto>builder()
                    .message("File or picture of book must not be empty")
                    .build();
        }
        if(!Objects.requireNonNull(dto.getFile().getOriginalFilename()).matches("^(.+)\\.(pdf|epub|word|fb2)$")){
            return ApiResponse.<BookFrontDto>builder()
                    .message("File type must be pdf, epub, word, fb2, txt")
                    .build();
        }
        if(!Objects.requireNonNull(dto.getPicture().getOriginalFilename()).matches("^(.+)\\.(png|jpeg|ico|jpg)$")){
            return ApiResponse.<BookFrontDto>builder()
                    .message("File type must be png, jpeg, ico, jpg")
                    .build();
        }
        Book book = bookMapper.toEntity(dto);
        Book save = bookRepository.save(book);
        return ApiResponse.<BookFrontDto>builder()
                .success(true)
                .message("Created!")
                .data(bookMapper.toDto(save))
                .build();
    }


    @SneakyThrows
    public ApiResponse<BookFrontDto> edit(Integer id, BookDto dto){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            return ApiResponse.<BookFrontDto>builder()
                    .message("Book not found")
                    .build();
        }
        Book book = optionalBook.get();
        if(dto.getFile().isEmpty() || dto.getPicture().isEmpty()){
            return ApiResponse.<BookFrontDto>builder()
                    .message("File or picture of book must not be empty")
                    .build();
        }
        if(!Objects.requireNonNull(dto.getFile().getOriginalFilename()).matches("^(.+)\\.(pdf|epub|word|fb2)$")){
            return ApiResponse.<BookFrontDto>builder()
                    .message("File type must be pdf, epub, word, fb2, txt")
                    .build();
        }
        if(!Objects.requireNonNull(dto.getPicture().getOriginalFilename()).matches("^(.+)\\.(png|jpeg|ico|jpg)$")){
            return ApiResponse.<BookFrontDto>builder()
                    .message("File type must be png, jpeg, ico, jpg")
                    .build();
        }
        bookMapper.update(dto, book);
        Book save = bookRepository.save(book);
        return ApiResponse.<BookFrontDto>builder()
                .success(true)
                .message("Edited!")
                .data(bookMapper.toDto(save))
                .build();
    }

    public ApiResponse<Object> delete(Integer id) {
        if (!bookRepository.existsById(id)) {
            return ApiResponse.builder()
                    .message("Book not found")
                    .build();
        }
        bookRepository.deleteById(id);
        return ApiResponse.builder()
                .success(true)
                .message("Deleted!")
                .build();
    }
}
