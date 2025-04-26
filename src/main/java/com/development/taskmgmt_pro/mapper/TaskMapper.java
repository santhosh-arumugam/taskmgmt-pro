package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateProjectDTO;
import com.development.taskmgmt_pro.dto.CreateTaskDTO;
import com.development.taskmgmt_pro.dto.TaskResponseByIdDTO;
import com.development.taskmgmt_pro.dto.TaskResponseDTO;
import com.development.taskmgmt_pro.enums.TaskPriority;
import com.development.taskmgmt_pro.enums.TaskStatus;
import com.development.taskmgmt_pro.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "description", target = "description")
    @Mapping(source = "priority", target = "priority", qualifiedByName = "stringToPriority")
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToStatus")
    Task toEntity(CreateTaskDTO dto);

    @Mapping(source = "project.projectId", target = "projectId")
    @Mapping(source = "user.userId", target = "userId")
    TaskResponseDTO toDto(Task task);

    Task toUpdateEntity(CreateTaskDTO dto, @MappingTarget Task task);

    TaskResponseByIdDTO toTaskResponseDTO(Task task);

    @Named("stringToPriority")
    default TaskPriority stringToPriority(String priority) {
        return priority != null && !priority.isEmpty() ? TaskPriority.valueOf(priority) : null;
    }

    @Named("stringToStatus")
    default TaskStatus stringToStatus(String status) {
        return status != null && !status.isEmpty() ? TaskStatus.valueOf(status) : null;
    }
}
