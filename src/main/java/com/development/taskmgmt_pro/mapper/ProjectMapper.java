package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateProjectDTO;
import com.development.taskmgmt_pro.dto.ProjectResponseDTO;
import com.development.taskmgmt_pro.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "projectId", ignore = true)
    Project toEntity(CreateProjectDTO dto);

    ProjectResponseDTO toDto(Project project);


}
