package com.blogApi.Repo;

import com.blogApi.entity.Category;
import com.blogApi.entity.Post;
import com.blogApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
