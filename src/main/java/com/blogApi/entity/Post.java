package com.blogApi.entity;

import com.blogApi.playload.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer postId;

    private String title;

    private String content;

    private String imageName;

    private String addedDate;

     @ManyToOne
    private  Category category;
    @ManyToOne
    private  User user;

    @OneToMany(mappedBy = "post",cascade=CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();

}
