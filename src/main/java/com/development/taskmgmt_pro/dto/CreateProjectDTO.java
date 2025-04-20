package com.development.taskmgmt_pro.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Getter
@Setter
public class CreateProjectDTO {

    @NotBlank(message = "Project Name cannot be empty")
    private String projectName;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @Pattern(regexp = "^(ACTIVE|COMPLETED|ON_HOLD)?$", message = "Project Status must be ACTIVE, COMPLETED, ON_HOLD or Null")
    private String projectStatus;
}
