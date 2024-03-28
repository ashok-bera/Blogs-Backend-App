package com.spring.blogs.services.impl;

import com.spring.blogs.dtos.CommentDto;
import com.spring.blogs.dtos.PostDto;
import com.spring.blogs.entities.Comment;
import com.spring.blogs.entities.Post;
import com.spring.blogs.exceptions.ResourceNotFoundException;
import com.spring.blogs.repositories.CommentRepo;
import com.spring.blogs.repositories.PostRepo;
import com.spring.blogs.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postdId) {
        Post post = this.postRepo.findById(postdId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postdId));
        Comment comment = dtoToComment(commentDto);
        comment.setPost(post);
        Comment createdComment = this.commentRepo.save(comment);
        return commentToDto(createdComment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = this.commentRepo.findAll();
        return comments.stream().map(comment -> commentToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","Id",id));
        return commentToDto(comment);
    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment  = this.commentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Comment","Id",id));
        this.commentRepo.delete(comment);
    }

    public Comment dtoToComment(CommentDto commentDto){
        return this.modelMapper.map(commentDto,Comment.class);
    }

    public CommentDto commentToDto(Comment comment){
        return this.modelMapper.map(comment,CommentDto.class);
    }

}
