package com.development.taskmgmt_pro.dto;

import com.development.taskmgmt_pro.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllTasksResponseDTO {
    private Long taskId;
    private String title;
    private LocalDate createdAt;
    private TaskPriority priority;
}
