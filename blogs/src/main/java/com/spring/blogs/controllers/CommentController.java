package com.spring.blogs.controllers;

import com.spring.blogs.dtos.ApiResponse;
import com.spring.blogs.dtos.CommentDto;
import com.spring.blogs.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId){
        CommentDto commentDto1 = this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtos = this.commentService.getAllComments();
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Integer id){
        CommentDto commentDto1 = this.commentService.getCommentById(id);
        return new ResponseEntity<>(commentDto1, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("id") Integer id){
        this.commentService.deleteComment(id);
        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully!",true), HttpStatus.OK);
    }
}
