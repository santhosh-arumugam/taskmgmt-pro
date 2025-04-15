package com.development.taskmgmt_pro.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String title;

    private String description;

    private String priority;

    private String status;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne()
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    @ManyToOne()
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
