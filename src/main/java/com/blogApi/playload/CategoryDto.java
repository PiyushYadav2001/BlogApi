package com.blogApi.playload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private  Integer categoryId;
     @NotBlank
     @Size(min = 3,message = "Minimum Size is 3")
    private  String categoryTitle;
    @NotEmpty
    @Size(min = 10,message = "Minimum Size is 10")
    private  String  categoryDescripton;
}
