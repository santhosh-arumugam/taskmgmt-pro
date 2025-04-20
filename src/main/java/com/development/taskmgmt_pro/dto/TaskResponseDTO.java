package com.development.taskmgmt_pro.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskResponseDTO {
    private Long taskId;
    private String title;
    private LocalDate createdAt;
    private Long userId;
    private Long projectId;
}
