package com.development.taskmgmt_pro.controller;

import com.development.taskmgmt_pro.dto.AllTasksResponseDTO;
import com.development.taskmgmt_pro.dto.CreateTaskDTO;
import com.development.taskmgmt_pro.dto.PagedResponseDTO;
import com.development.taskmgmt_pro.dto.TaskResponseDTO;
import com.development.taskmgmt_pro.exception.InvalidSortException;
import com.development.taskmgmt_pro.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private static final Set<String> VALID_SORT_FIELDS = Set.of("createdAt", "priority");
    private static final Set<String> VALID_SORT_DIRECTIONS = Set.of("asc", "desc");

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskDTO dto){
        TaskResponseDTO taskResponseDTO = taskService.createTask(dto);
        return new ResponseEntity<>(taskResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<AllTasksResponseDTO>> findAllTasks(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size,
            @RequestParam (defaultValue = "createdAt,asc") List<String> sort) {
        Sort sortObject = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, sortObject);
        Page<AllTasksResponseDTO> pageResponse = taskService.findAllTasks(pageable);
        PagedResponseDTO<AllTasksResponseDTO> pagedResponseDTO = new PagedResponseDTO<>(
                pageResponse.getContent(),
                pageResponse.getNumber(),
                pageResponse.getSize(),
                pageResponse.getTotalElements(),
                pageResponse.getTotalPages());
        return new ResponseEntity<>(pagedResponseDTO, HttpStatus.OK);
    }

    private Sort parseSort(List<String> sortParams) {
        List<Sort.Order> orders = sortParams.stream().map(param ->
        {
            String[] parts = param.split(",");
            if(parts.length != 2) {
                throw new InvalidSortException("Parameters required for sorting should be in valid format field, direction");
            }
            String field = parts[0].trim();
            String direction = parts[1].trim().toLowerCase();
            if (!VALID_SORT_FIELDS.contains(field)) {
                throw new InvalidSortException("Invalid Sort field: "+field+", Valid Sort fields are: "+VALID_SORT_FIELDS);
            }
            if (!VALID_SORT_DIRECTIONS.contains(direction)) {
                throw new InvalidSortException("Invalid Sort direction: "+direction+", Valid Sort directions are: "+VALID_SORT_DIRECTIONS);
            }
            return new Sort.Order(Sort.Direction.fromString(direction), field);
        } ).toList();
        return Sort.by(orders);
    }
}
