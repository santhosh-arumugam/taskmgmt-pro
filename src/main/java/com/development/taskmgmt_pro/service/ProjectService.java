package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.AllProjectsResponseDTO;
import com.development.taskmgmt_pro.dto.AllUsersResponseDTO;
import com.development.taskmgmt_pro.dto.CreateProjectDTO;
import com.development.taskmgmt_pro.dto.ProjectResponseDTO;
import com.development.taskmgmt_pro.exception.DuplicateProjectException;
import com.development.taskmgmt_pro.mapper.ProjectMapper;
import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Transactional
    public ProjectResponseDTO createProject(CreateProjectDTO dto) {
        if (projectRepository.findByProjectName(dto.getProjectName()).isPresent()) {
            throw new DuplicateProjectException("Project name already exists");
        }
        Project savedProject = projectRepository.save(projectMapper.toEntity(dto));
        return projectMapper.toDto(savedProject);
    }

    public Page<AllProjectsResponseDTO> findAllProjects(Pageable pageable) {
        Page<Project> pagedProjects = projectRepository.findAll(pageable);
        return pagedProjects.map(project -> new AllProjectsResponseDTO(project.getProjectId(), project.getProjectName()));
    }
}
