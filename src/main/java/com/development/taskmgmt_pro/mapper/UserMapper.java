package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseByIdDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.enums.JobRole;
import com.development.taskmgmt_pro.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)//To ignore user ID mapping from DTO to entity
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "jobRole", source = "jobRole", qualifiedByName = "stringToJobRoles")
    User toEntity(CreateUserDTO dto);

    User toUpdateEntity(CreateUserDTO dto, @MappingTarget User user);

    UserResponseDTO toDto(User user);

    UserResponseByIdDTO toUserResponseDTO(User user);

    @Named("stringToJobRoles")
   default JobRole stringToJobRole(String jobRole) {
       return jobRole != null && !jobRole.isEmpty() ? JobRole.valueOf(jobRole) : null;
   }
}
