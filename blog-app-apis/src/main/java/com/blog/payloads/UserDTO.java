package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    @NonNull
    @Size(min = 4, message = "Username must contain minimum 4 characters")
    private String name;

    @Email(message = "Email address is not valid")
    @NotBlank(message = "enter email")
    private String email;

    @Size(min = 4, max = 10, message = "Password size is between 4 to 10 characters")
    private String password;

    @NotBlank(message = "fill about")
    private String about;
}
