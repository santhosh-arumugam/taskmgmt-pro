package com.development.taskmgmt_pro.dto;

import com.development.taskmgmt_pro.enums.TaskPriority;
import com.development.taskmgmt_pro.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseByIdDTO {
    private Long taskId;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
