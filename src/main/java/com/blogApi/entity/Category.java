package com.blogApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "title")
    private  String categoryTitle;

    @Column(name = "descripton")
    private  String  categoryDescripton;

     @OneToMany(mappedBy = "category",cascade =CascadeType.ALL)
    private List<Post> posts=new ArrayList<>();
}
