package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.enums.JobRole;
import com.development.taskmgmt_pro.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)//To ignore user ID mapping from DTO to entity
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "fullName", source = "fullName")//To ignore createdAt mapping from DTO to entity
    @Mapping(target = "jobRole", source = "jobRole", qualifiedByName = "stringToJobRole")
    User toEntity(CreateUserDTO dto);

    UserResponseDTO toDto(User user);

    @Named("stringToJobRole")
   default JobRole stringToJobRole(String jobRole) {
       return jobRole != null && !jobRole.isEmpty() ? JobRole.valueOf(jobRole) : null;
   }
}
