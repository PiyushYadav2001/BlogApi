package com.blogApi.playload;

import com.blogApi.entity.Category;
import com.blogApi.entity.Comment;
import com.blogApi.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer  postId;

    private  String title;

    private String content;

    private String imageName;

    private String addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments=new HashSet<>();
}
