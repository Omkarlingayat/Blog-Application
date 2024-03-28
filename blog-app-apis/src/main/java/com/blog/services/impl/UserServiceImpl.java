package com.blog.services.impl;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDTO;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.blog.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    // create user
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userDtoToUser(userDTO);
        userRepository.save(user);
        return usetToUserDto(user);
    }

    // update user
    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        if(userDTO.getEmail() != null){
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getPassword() != null){
            user.setPassword(userDTO.getPassword());
        }
        if(userDTO.getName() != null){
            user.setName(userDTO.getName());
        }
        if(userDTO.getAbout() != null){
            user.setAbout(userDTO.getAbout());
        }

        userRepository.save(user);

        return usetToUserDto(user);
    }

    // get user by its id
    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        UserDTO userDTO = usetToUserDto(user);
        return userDTO;
    }


    // delete user by its id
    @Override
    public void deleteUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        userRepository.delete(user);
    }

    // get all users
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = users.stream().map(user -> this.usetToUserDto(user)).collect(Collectors.toList());
        return userDTOList;
    }

    // convert User to UserDTO
    public User userDtoToUser(UserDTO userDTO){
        /*User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setAbout(userDTO.getAbout());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        */

        User user = modelMapper.map(userDTO,User.class);
        return user;
    }

    // convert UserDTO to User
    public UserDTO usetToUserDto(User user){
        /*UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setAbout(user.getAbout());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        */

        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        return userDTO;
    }
}
