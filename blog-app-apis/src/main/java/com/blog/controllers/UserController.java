package com.blog.controllers;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // create user
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User created successfully",true),HttpStatus.CREATED);
    }

    // update user
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDTO updatedUserDTO, @PathVariable("userId")Integer userId){
        userService.updateUser(updatedUserDTO,userId);
        return ResponseEntity.ok(new ApiResponse("user updated successfully",true));
    }

    // delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Integer userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok(new ApiResponse("user deleted successfully",true));
    }

    // get user profile by its id
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId")Integer userId){
        UserDTO userDTO = userService.getUserById(userId);
        return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
    }

    // get all users
    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
