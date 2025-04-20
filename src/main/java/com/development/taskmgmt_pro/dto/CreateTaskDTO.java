package com.development.taskmgmt_pro.dto;

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
}
