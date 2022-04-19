package com.project.school_book.mapper;

import com.project.school_book.dto.GroupDto;
import com.project.school_book.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    Group toEntity(GroupDto dto);
    List<Group> toEntity(List<GroupDto> dto);
    GroupDto toDto(Group group);
    List<GroupDto> toDto(List<Group> groups);

    void update(GroupDto dto, @MappingTarget Group group);
}
