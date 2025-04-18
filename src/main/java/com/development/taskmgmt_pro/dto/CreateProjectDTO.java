package com.development.taskmgmt_pro.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateProjectDTO {

    @NotBlank(message = "Project name should not be blank")
    private String projectName;

}
