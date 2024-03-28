package com.spring.blogs.services;

import com.spring.blogs.dtos.CommentDto;
import com.spring.blogs.entities.Comment;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer pid);
    List<CommentDto> getAllComments();
    CommentDto getCommentById(Integer id);
     void deleteComment(Integer id);
}
