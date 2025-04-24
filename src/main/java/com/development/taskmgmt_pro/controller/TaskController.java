package com.development.taskmgmt_pro.controller;

import com.development.taskmgmt_pro.dto.*;
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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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
            @RequestParam (defaultValue = "createdAt,asc") String sort) {
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

    private Sort parseSort(String sortParams) {
        String[] paramArray = sortParams.split(",");
        if (paramArray.length == 0 || paramArray.length % 2 != 0) {
            throw new InvalidSortException("Parameters required for sorting should be in valid format field, Example: createdAt, asc");
        }
        List<Sort.Order> orders = IntStream.range(0, paramArray.length/2).mapToObj(i ->
        {
            String field = paramArray[i*2].trim();
            String direction = paramArray[i*2+1].trim().toLowerCase();
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

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseByIdDTO> findById(@PathVariable Long id) {
       TaskResponseByIdDTO response = taskService.findByTaskId(id);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        System.out.println("Request received to delete the Task details for Task ID: "+id);
        taskService.deleteByTaskId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
