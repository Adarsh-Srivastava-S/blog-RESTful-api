package com.rest.blogapi.controllers;

import com.rest.blogapi.model.User;
import com.rest.blogapi.payloads.ApiResponse;
import com.rest.blogapi.payloads.UserDto;
import com.rest.blogapi.services.UserService;
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
    private UserService userService;
    //POST create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUser=this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    //PUT update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Integer id)
    {
        UserDto updatedUser=this.userService.updateUser(userDto,id);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE user delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer id)
    {
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }
    //GET all user get
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    //GET user By  id
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }
}
