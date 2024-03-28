package com.spring.blogs.controllers;

import com.spring.blogs.dtos.ApiResponse;
import com.spring.blogs.dtos.UserDto;
import com.spring.blogs.services.UserService;
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

    @PostMapping("/")
    public ResponseEntity<UserDto> cretaeUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
    //path uri var
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid)
    {
        UserDto updatedUserDto = this.userService.updateUser(userDto,uid);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
    {
        this.userService.deleteUser(uid);
        //return new ResponseEntity<>(Map.of("message","user deleted successfully"),HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("User removed Successfully",true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser()
    {
        List<UserDto> userDtos = this.userService.getAll();
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }
    @GetMapping("/{userID}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userID") Integer uid)
    {
        UserDto userDto  = this.userService.getUserById(uid);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    //post - create

}
