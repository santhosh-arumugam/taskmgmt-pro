package com.development.taskmgmt_pro.mapper;

import com.development.taskmgmt_pro.dto.CreateProjectDTO;
import com.development.taskmgmt_pro.dto.ProjectResponseDTO;
import com.development.taskmgmt_pro.enums.ProjectStatus;
import com.development.taskmgmt_pro.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "projectStatus", source = "projectStatus", qualifiedByName = "stringToProjectStatus")
    Project toEntity(CreateProjectDTO dto);

    ProjectResponseDTO toDto(Project project);

    @Named("stringToProjectStatus")
    default ProjectStatus stringToProjectStatus(String projectStatus) {
        return projectStatus != null && !projectStatus.isEmpty() ? ProjectStatus.valueOf(projectStatus) : null;
    }
}
