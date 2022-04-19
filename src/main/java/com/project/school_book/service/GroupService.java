package com.project.school_book.service;

import com.project.school_book.dto.ApiResponse;
import com.project.school_book.dto.GroupDto;
import com.project.school_book.entity.Group;
import com.project.school_book.mapper.GroupMapper;
import com.project.school_book.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    final GroupRepository groupRepository;
    final GroupMapper groupMapper;

    public ApiResponse<GroupDto> add(GroupDto dto) {
        groupRepository.save(groupMapper.toEntity(dto));
        return ApiResponse.<GroupDto>builder()
                .message("Created!")
                .success(true)
                .data(dto)
                .build();
    }

    public ApiResponse<GroupDto> edit(Integer id, GroupDto dto) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()){
            return ApiResponse.<GroupDto>builder()
                    .message("Group not found")
                    .build();
        }
        groupMapper.update(dto, optionalGroup.get());
        groupRepository.save(optionalGroup.get());
        return ApiResponse.<GroupDto>builder()
                .success(true)
                .message("Edited!")
                .data(dto)
                .build();
    }

    public ApiResponse<Object> delete(Integer id){
        if(!groupRepository.existsById(id)){
            return ApiResponse.builder()
                    .message("Group not found")
                    .build();
        }
        groupRepository.deleteById(id);
        return ApiResponse.builder()
                .message("Deleted!")
                .success(true)
                .build();
    }
}
