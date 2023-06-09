package com.blogApi.Repo;

import com.blogApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo  extends JpaRepository<Comment,Integer> {

}
