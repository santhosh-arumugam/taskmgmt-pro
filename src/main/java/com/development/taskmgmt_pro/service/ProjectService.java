package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.CreateProjectDTO;
import com.development.taskmgmt_pro.dto.ProjectResponseDTO;
import com.development.taskmgmt_pro.exception.DuplicateProjectException;
import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponseDTO createProject(CreateProjectDTO dto) {
        if (projectRepository.findByProjectName(dto.getProjectName()).isPresent()) {
            throw new DuplicateProjectException("Project name already exists");
        }
        Project project = new Project();
        project.setProjectName(dto.getProjectName());
        Project savedProject = projectRepository.save(project);

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setProjectId(savedProject.getProjectId());
        projectResponseDTO.setProjectName(savedProject.getProjectName());
        return projectResponseDTO;
    }
}
