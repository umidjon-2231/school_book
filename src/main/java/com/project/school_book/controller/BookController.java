package com.project.school_book.controller;

import com.project.school_book.dto.ApiResponse;
import com.project.school_book.dto.BookDto;
import com.project.school_book.dto.BookFrontDto;
import com.project.school_book.entity.Attachment;
import com.project.school_book.entity.Book;
import com.project.school_book.entity.Group;
import com.project.school_book.entity.enums.Language;
import com.project.school_book.mapper.BookMapper;
import com.project.school_book.mapper.GroupMapper;
import com.project.school_book.repository.BookRepository;
import com.project.school_book.repository.GroupRepository;
import com.project.school_book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    final BookService bookService;
    final BookRepository bookRepository;
    final BookMapper bookMapper;
    final GroupRepository groupRepository;

    @PostMapping
    public HttpEntity<?> add(@Valid @ModelAttribute BookDto dto){
        ApiResponse<BookFrontDto> apiResponse = bookService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@Valid @ModelAttribute BookDto dto) {
        ApiResponse<BookFrontDto> apiResponse = bookService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse<Object> apiResponse=bookService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(bookMapper.toDto(optionalBook.get()));
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(bookMapper.toDto(bookRepository.findAll()));
    }

    @GetMapping("/file/{id}")
    public HttpEntity<?> getFile(@PathVariable Integer id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Attachment file = optionalBook.get().getFile();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.getSize())
                .body(file.getBytes());
    }

    @GetMapping("/picture/{id}")
    public HttpEntity<?> getPicture(@PathVariable Integer id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Attachment picture = optionalBook.get().getPicture();
        return ResponseEntity.ok().header("Content-Type", picture.getType()).body(picture.getBytes());
    }


    @GetMapping("/search")
    public HttpEntity<?> search(@RequestParam(value = "q", required = false) String key,
                                @RequestParam(value = "class", required = false) Integer classNumber,
                                @RequestParam(value = "lang", required = false) String lang){
        if(key==null && classNumber==null && lang==null){
            return ResponseEntity.ok().body(bookMapper.toDto(bookRepository.findAll()));
        }
        List<Integer> groupIds=new ArrayList<>();
        if(classNumber==null){
            groupIds= groupRepository.findAll().stream().map(Group::getClassNumber).toList();
        }else {
            groupIds.add(classNumber);
        }
        List<Language> languages=new ArrayList<>();
        if(lang==null){
            languages.addAll(Arrays.stream(Language.values()).toList());
        }else {
            languages.add(Language.valueOf(lang));
        }
        if(key!=null){
            key="%"+key+"%";
        }else {
            key="any";
        }
        List<Book> books = bookRepository.findAllByNameIsLikeAndGroup_ClassNumberInAndLanguageIn(
                key, groupIds, languages);
        return ResponseEntity.ok().body(bookMapper.toDto(books));
    }

}
