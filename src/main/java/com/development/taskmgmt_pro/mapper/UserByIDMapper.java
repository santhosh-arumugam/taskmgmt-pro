package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.UserResponseByIdDTO;
import com.development.taskmgmt_pro.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserByIDMapper {
    UserResponseByIdDTO toDto(User user);
}
