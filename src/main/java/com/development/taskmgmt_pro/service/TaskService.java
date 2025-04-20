package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.CreateTaskDTO;
import com.development.taskmgmt_pro.dto.TaskResponseDTO;
import com.development.taskmgmt_pro.exception.DuplicateTaskException;
import com.development.taskmgmt_pro.exception.ResourceNotFoundException;
import com.development.taskmgmt_pro.mapper.TaskMapper;
import com.development.taskmgmt_pro.model.Project;
import com.development.taskmgmt_pro.model.Task;
import com.development.taskmgmt_pro.model.User;
import com.development.taskmgmt_pro.repository.ProjectRepository;
import com.development.taskmgmt_pro.repository.TaskRepository;
import com.development.taskmgmt_pro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskResponseDTO createTask(CreateTaskDTO dto) {

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + dto.getProjectId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: "+ dto.getUserId()));

        if (taskRepository.findByTitle(dto.getTitle()).isPresent()) {
            throw new DuplicateTaskException("Task Title already exists");
        }

//        Task task = new Task();
        Task task = taskMapper.toEntity(dto);
        task.setProject(project);
        task.setUser(user);
//        task.setTitle(dto.getTitle());



        Task savedTask = taskRepository.save(task);



        return taskMapper.toDto(savedTask);
    }
}
