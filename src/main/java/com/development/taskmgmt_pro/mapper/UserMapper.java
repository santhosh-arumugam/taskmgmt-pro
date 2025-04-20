package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateUserDTO;
import com.development.taskmgmt_pro.dto.UserResponseDTO;
import com.development.taskmgmt_pro.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true) //To ignore user ID mapping from DTO to entity
    User toEntity(CreateUserDTO dto);

    UserResponseDTO toDto(User user);
}
