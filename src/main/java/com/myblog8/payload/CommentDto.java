package com.myblog8.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    @NotEmpty
    @Size(min = 3,message = "name title should be at least 2 characters")
    private String name;

    @NotEmpty
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty
    @Size(min = 5,message = "body should be at least 5 characters")
    private String body;
}
