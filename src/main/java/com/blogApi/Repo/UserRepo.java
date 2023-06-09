package com.blogApi.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApi.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {



    Optional<User> findByEmail(String email);
}
