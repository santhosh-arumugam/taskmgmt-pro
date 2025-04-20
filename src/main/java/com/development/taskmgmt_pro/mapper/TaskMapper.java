package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateTaskDTO;
import com.development.taskmgmt_pro.dto.TaskResponseDTO;
import com.development.taskmgmt_pro.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task toEntity(CreateTaskDTO dto);

    @Mapping(source = "project.projectId", target = "projectId")
    @Mapping(source = "user.userId", target = "userId")
    TaskResponseDTO toDto(Task task);
}
