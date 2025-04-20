package com.development.taskmgmt_pro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "Username cannot be empty")
    private String userName;

    @Email(message = "Invalid emailID format")
    private String emailId;

}
