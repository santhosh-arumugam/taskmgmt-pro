package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.dto.AllTasksResponseDTO;
import com.development.taskmgmt_pro.dto.CreateTaskDTO;
import com.development.taskmgmt_pro.dto.TaskResponseByIdDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        Task task = taskMapper.toEntity(dto);
        task.setProject(project);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Transactional
    public Page<AllTasksResponseDTO> findAllTasks(Pageable pageable) {
        Page<Task> pagedTasks = taskRepository.findAll(pageable);
        return pagedTasks.map(task -> new AllTasksResponseDTO(task.getTaskId(), task.getTitle(), task.getCreatedAt(), task.getPriority()));
    }

    @Transactional
    public TaskResponseByIdDTO findByTaskId(Long taskId) {
        Task fetchedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task ID: "+taskId+" does not exists"));
        return taskMapper.toTaskResponseDTO(fetchedTask);
    }
}
