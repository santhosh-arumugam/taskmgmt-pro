package com.development.taskmgmt_pro.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateTaskDTO {
    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotNull(message = "ProjectId is required")
    private Long projectId;

    @NotNull(message = "UserID is required")
    private Long userId;

    @Size(max = 250)
    private String description;

    @Pattern(regexp = "^(LOW|MEDIUM|HIGH)?$", message = "Task Priority must be LOW, MEDIUM, HIGH or Null")
    private String priority;

    @Pattern(regexp = "^(OPEN|IN_PROGRESS|DONE)?$", message = "Task Status must be OPEN, IN_PROGRESS, DONE or Null")
    private String status;
}
