package com.development.taskmgmt_pro.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Email(message = "Invalid emailID format", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String emailId;

    @Size(max = 100)
    private String fullName;

    @Pattern(regexp = "^(MANAGER|DEVELOPER|PRODUCT_OWNER|TESTER)?$", message = "Job role must be MANAGER, DEVELOPER, PRODUCT_OWNER, TESTER  or null")
    private String jobRole;

    private String password;
}
