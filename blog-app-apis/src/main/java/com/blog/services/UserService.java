package com.blog.services;
import com.blog.payloads.UserDTO;

import java.util.List;

public interface UserService {
    // create user
    UserDTO createUser(UserDTO userDTO);

    // update user
    UserDTO updateUser(UserDTO userDTO, Integer userId);

    // find user by its id
    UserDTO getUserById(Integer userId);

    // delete user by its id
    void deleteUserById(Integer userId);

    // get all users
    List<UserDTO> getAllUsers();
}
