package com.blogApi.Controller;

import com.blogApi.Service.UserService;
import com.blogApi.playload.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto) {
        UserDto createUser = this.service.createUser(userDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer uId) {
		UserDto UpdatedUser = this.service.updateUser(userDto, uId);
		return  ResponseEntity.ok(UpdatedUser);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
         this.service.deleteUser(uid);
         return new  ResponseEntity(Map.of("message","User Deleted Sucesddfully"),HttpStatus.OK);
    }

    @GetMapping("/")
    public  ResponseEntity<List<UserDto>> getAllUsers() {
          return ResponseEntity.ok(this.service.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.service.getUserById(userId));
    }
}
