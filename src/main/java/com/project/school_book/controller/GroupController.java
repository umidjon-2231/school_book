package com.project.school_book.controller;

import com.project.school_book.dto.ApiResponse;
import com.project.school_book.dto.GroupDto;
import com.project.school_book.entity.Group;
import com.project.school_book.mapper.BookMapper;
import com.project.school_book.mapper.GroupMapper;
import com.project.school_book.repository.GroupRepository;
import com.project.school_book.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {
    final GroupRepository groupRepository;
    final GroupMapper groupMapper;
    final GroupService groupService;
    final BookMapper bookMapper;

    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(groupMapper.toDto(groupRepository.findAll()));
    }

    @PostMapping()
    public HttpEntity<?> add(@RequestBody GroupDto dto){
        ApiResponse<GroupDto> apiResponse=groupService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @PatchMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody GroupDto dto){
        ApiResponse<GroupDto> apiResponse=groupService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse<Object> apiResponse = groupService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }

    @GetMapping("/{id}/books")
    public HttpEntity<?> getBooks(@PathVariable Integer id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if(optionalGroup.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(bookMapper.toDto(optionalGroup.get().getBooks()));
    }
}
