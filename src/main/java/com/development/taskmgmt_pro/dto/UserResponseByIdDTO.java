package com.development.taskmgmt_pro.dto;

import com.development.taskmgmt_pro.enums.JobRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseByIdDTO {
    private Long userId;
    private String userName;
    private String fullName;
    private String emailId;
    private JobRole jobRole;
    private LocalDate createdAt;
}
