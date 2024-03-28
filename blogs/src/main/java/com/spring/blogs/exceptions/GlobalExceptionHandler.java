package com.spring.blogs.exceptions;

import com.spring.blogs.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
       Map<String,String> resp = new HashMap<>();
       long id = ex.getFiledValue();
       String resource = ex.getResourceName();
       resp.put("msg","Provided " + resource + ": id doesnt exist :"+id);
       resp.put("success","false");
       return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> argumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String filedName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            resp.put(filedName,message);
        });
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }


}
