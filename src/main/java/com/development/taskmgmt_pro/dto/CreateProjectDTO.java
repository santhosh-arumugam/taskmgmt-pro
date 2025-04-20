package com.development.taskmgmt_pro.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateProjectDTO {

    @NotBlank(message = "Project Name cannot be empty")
    private String projectName;

}
