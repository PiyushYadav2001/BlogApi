package com.blogApi.security;

import com.blogApi.Exception.ResourceNotFoundException;
import com.blogApi.Exception.UserNotFoundException;
import com.blogApi.Repo.UserRepo;
import com.blogApi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailSevice  implements UserDetailsService {

    @Autowired
     private UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from Database by username
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User", "Email", username));
        return user;
    }
}
