package com.development.taskmgmt_pro.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(unique = true, nullable = false)
    private String projectName;

    @Column //This column is optional by default
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String projectStatus;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> task;

}
