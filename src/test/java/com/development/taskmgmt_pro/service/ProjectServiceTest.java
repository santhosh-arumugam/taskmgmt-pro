package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.*;
import com.development.taskmgmt_pro.enums.ProjectStatus;
import com.development.taskmgmt_pro.mapper.ProjectMapper;
import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @InjectMocks
    private ProjectService projectService;

    private CreateProjectDTO createProjectDTO;
    private ProjectResponseDTO projectResponseDTO;
    private Project project;
    private Pageable pageable;
    private AllProjectsResponseDTO allProjectsResponseDTO;
    private ProjectResponseByIdDTO projectResponseByIdDTO;

    @BeforeEach
    void setUp() {

        createProjectDTO = new CreateProjectDTO();
        createProjectDTO.setProjectName("External Channels Integration");
        createProjectDTO.setDescription("Integrating channels");
        createProjectDTO.setStartDate(LocalDate.of(2025,4,30));
        createProjectDTO.setEndDate(LocalDate.of(2025,6,15));
        createProjectDTO.setProjectStatus("ACTIVE");

        project = new Project();
        project.setProjectId(1L);
        project.setProjectName("External Channels Integration");
        project.setDescription("Integrating channels");
        project.setStartDate(LocalDate.of(2025,4,30));
        project.setEndDate(LocalDate.of(2025,6,15));
        project.setProjectStatus(ProjectStatus.ACTIVE);

        projectResponseDTO = new ProjectResponseDTO(1L, "External Channels Integration");

        pageable = PageRequest.of(0, 2);

        allProjectsResponseDTO = new AllProjectsResponseDTO(1L, "External Channels Integration");

        projectResponseByIdDTO = new ProjectResponseByIdDTO();
        projectResponseByIdDTO.setProjectId(1L);
        projectResponseByIdDTO.setProjectName("External Channels Integration");
        projectResponseByIdDTO.setDescription("Integrating channels");
        projectResponseByIdDTO.setStartDate(LocalDate.of(2025,4,30));
        projectResponseByIdDTO.setEndDate(LocalDate.of(2025,6,15));
        projectResponseByIdDTO.setProjectStatus(ProjectStatus.ACTIVE);
    }

    @Test
    void createProject_success_returnProjectResponseDTO() {
        when(projectRepository.findByProjectName("External Channels Integration")).thenReturn(Optional.empty());
        when(projectMapper.toEntity(createProjectDTO)).thenReturn(project);
        when(projectRepository.save(any())).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectResponseDTO);

        ProjectResponseDTO result = projectService.createProject(createProjectDTO);

        assertNotNull(result);
        assertEquals(1L, result.getProjectId());
        assertEquals("External Channels Integration", result.getProjectName());
        verify(projectRepository).save(any());
        verify(projectRepository).findByProjectName("External Channels Integration");
    }

    @Test
    void findAllProjects_success_returnPagedAllProjectsResponseDTO() {
        Page<Project> projectPage = new PageImpl<>(List.of(project));
        when(projectRepository.findAll(pageable)).thenReturn(projectPage);

        Page<AllProjectsResponseDTO> result = projectService.findAllProjects(pageable);

        assertNotNull(result);
        assertEquals(1L, result.getContent().getFirst().getProjectId());
        assertEquals("External Channels Integration", result.getContent().getFirst().getProjectName());
        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getTotalElements());
        verify(projectRepository).findAll(pageable);
    }

    @Test
    void findProjectById_success_return_projectResponseByIdDTO() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toProjectResponseDTO(any())).thenReturn(projectResponseByIdDTO);

        ProjectResponseByIdDTO result = projectService.findProjectById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getProjectId());
        assertEquals("External Channels Integration", result.getProjectName());
        assertEquals("Integrating channels", result.getDescription());
        assertEquals(LocalDate.of(2025,4,30), result.getStartDate());
        assertEquals(LocalDate.of(2025,6,15), result.getEndDate());
        assertEquals(ProjectStatus.ACTIVE, result.getProjectStatus());
        verify(projectRepository).findById(1L);
    }

    @Test
    void deleteById_success_existingId() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        projectService.deleteById(1L);

        verify(projectRepository).findById(1L);
    }

    @Test
    void updateById_success_return_projectResponseByIdDTO() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toUpdateEntity(createProjectDTO, project)).thenReturn(project);
        when(projectRepository.save(any())).thenReturn(project);
        when(projectMapper.toProjectResponseDTO(project)).thenReturn(projectResponseByIdDTO);

        ProjectResponseByIdDTO result = projectService.updateById(1L, createProjectDTO);

        assertNotNull(result);
        assertEquals(1L, result.getProjectId());
        assertEquals("External Channels Integration", result.getProjectName());
        assertEquals("Integrating channels", result.getDescription());
        assertEquals(LocalDate.of(2025,4,30), result.getStartDate());
        assertEquals(LocalDate.of(2025,6,15), result.getEndDate());
        assertEquals(ProjectStatus.ACTIVE, result.getProjectStatus());
        verify(projectRepository).findById(1L);
        verify(projectRepository).save(project);
    }
}
