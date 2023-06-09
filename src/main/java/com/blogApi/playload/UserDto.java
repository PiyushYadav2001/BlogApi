package com.blogApi.playload;

import com.blogApi.entity.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	 private int id;
	 @NotEmpty
	 @Size(min = 4,message = "USername must be min of 4 charcters ")
	 private String name;
	 @NotEmpty
	 @Email(message = "Email Address is Not Vaild ")
	 private String email;
	 @NotEmpty
	 @Size(min = 3,message = "PAssword Must be MIn 3 Charcters ")
	 private String password;
	 @NotEmpty
	 @Size(min = 10,message = "About must be 10 characters")
	 private String about;

     private Set<CommentDto> comments=new HashSet<>();

	 

}
