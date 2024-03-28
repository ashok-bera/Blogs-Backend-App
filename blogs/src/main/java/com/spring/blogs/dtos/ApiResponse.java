package com.spring.blogs.dtos;

public class ApiResponse {
    String msg;
    boolean status;

    public ApiResponse(String msg,boolean status){
        this.msg = msg;
        this.status = status;
    }

}
