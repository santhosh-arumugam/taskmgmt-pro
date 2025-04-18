package com.development.taskmgmt_pro.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectResponseDTO {
    private Long projectId;
    private String projectName;
}
