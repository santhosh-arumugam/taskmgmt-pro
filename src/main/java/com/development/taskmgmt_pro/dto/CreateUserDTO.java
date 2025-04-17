package com.development.taskmgmt_pro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "Username cannot be empty")
    private String userName;

    @Email(message = "emailID invalid format")
    private String emailId;

}
