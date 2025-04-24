package com.development.taskmgmt_pro.controller;

import com.development.taskmgmt_pro.dto.*;
import com.development.taskmgmt_pro.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) { this.projectService = projectService; }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody CreateProjectDTO dto) {
        ProjectResponseDTO response = projectService.createProject(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<AllProjectsResponseDTO>> findAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<AllProjectsResponseDTO> pagedProjects = projectService.findAllProjects(PageRequest.of(page, size));
        PagedResponseDTO<AllProjectsResponseDTO> response = new PagedResponseDTO<>(
                pagedProjects.getContent(),
                pagedProjects.getNumber(),
                pagedProjects.getSize(),
                pagedProjects.getTotalElements(),
                pagedProjects.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseByIdDTO> findById(@PathVariable Long id) {
        ProjectResponseByIdDTO response = projectService.findProjectById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByProjectId(@PathVariable Long id) {
        System.out.println("Request received to delete the Project details for Project ID :"+id);
        projectService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
