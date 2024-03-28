package com.spring.blogs.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String filedname;
    long filedValue;


    public ResourceNotFoundException(String resourceName,String filedname,long filedValue){
        //super(String.format("%s with given %s doesnot exist: %s",resourceName,filedname,filedValue));
        this.resourceName = resourceName;
        this.filedname = filedname;
        this.filedValue = filedValue;
    }


}
