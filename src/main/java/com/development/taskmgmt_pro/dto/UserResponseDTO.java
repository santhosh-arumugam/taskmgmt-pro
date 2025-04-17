package com.development.taskmgmt_pro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String userName;
    private String emailId;
}
