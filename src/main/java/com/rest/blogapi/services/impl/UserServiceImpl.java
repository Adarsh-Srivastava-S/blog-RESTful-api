package com.rest.blogapi.services.impl;

import com.rest.blogapi.model.User;
import com.rest.blogapi.payloads.UserDto;
import com.rest.blogapi.repositories.UserRepo;
import com.rest.blogapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import com.rest.blogapi.exceptions.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto)
    {
        User user=this.dtoToUser((userDto));
        User savedUser=this.userRepo.save(user);
        return this.userToDto(savedUser);
    }
    @Override
    public UserDto updateUser(UserDto userDto,Integer userID)
    {
        User user=this.userRepo.findById(userID).orElseThrow(()->new ResourceNotFoundEsception("User","id",userID));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        User updatedUser=this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }
    @Override
    public UserDto getUserById(Integer userId){
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundEsception("User","id",userId));

        return this.userToDto(user);
    }
    @Override
    public List<UserDto> getAllUsers(){
        List<User> users=this.userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user -> userToDto(user)).collect(Collectors.toList());


        return userDtos;
    }
    @Override
    public void deleteUser(Integer userId){
       User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundEsception("User","id",userId));
       this.userRepo.deleteById(userId);
    }
    private User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }
    private UserDto userToDto(User user){
        UserDto userDto= this.modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
