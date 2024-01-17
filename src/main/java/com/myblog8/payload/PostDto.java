package com.myblog8.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 3,message = "post title should be at least 3 characters")
    private String title;

    @NotEmpty
    @Size(min = 4,message = "description title should be at least 4 characters")
    private String description;

    @NotEmpty
    @Size(min = 5,message = "content title should be at least 5 characters")
    private String content;
}
