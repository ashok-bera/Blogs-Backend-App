package com.spring.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int cId;
    @NotBlank
    @Size(min = 4, message = "min size of title shuld be 4 chars")
    private String title;
    @NotBlank
    @Size(min=5,message = "min size of title shuld be 5 chars")
    private String description;

}
