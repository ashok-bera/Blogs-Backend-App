package com.spring.blogs.dtos;


import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;

    private String password;

}
