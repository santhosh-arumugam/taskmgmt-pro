package com.development.taskmgmt_pro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllProjectsResponseDTO {
    private Long projectId;
    private String projectName;
}
