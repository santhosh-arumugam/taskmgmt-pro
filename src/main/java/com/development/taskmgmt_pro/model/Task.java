package com.development.taskmgmt_pro.model;

import com.development.taskmgmt_pro.enums.TaskPriority;
import com.development.taskmgmt_pro.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne()
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    @ManyToOne()
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
