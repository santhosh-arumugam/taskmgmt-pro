package com.development.taskmgmt_pro.controller;

import com.development.taskmgmt_pro.dto.CreateTaskDTO;
import com.development.taskmgmt_pro.dto.TaskResponseDTO;
import com.development.taskmgmt_pro.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskDTO dto){
        TaskResponseDTO taskResponseDTO = taskService.createTask(dto);
        return new ResponseEntity<>(taskResponseDTO,HttpStatus.CREATED);
    }
}
