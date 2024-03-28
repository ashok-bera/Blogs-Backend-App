package com.spring.blogs.services;

import com.spring.blogs.dtos.PostDto;
import com.spring.blogs.dtos.PostResponse;
import com.spring.blogs.entities.Category;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer Uid, Integer CatId);
    PostDto updatePost(PostDto postDto,Integer pId);

    void deletePost(Integer pId);
    PostDto getPostById(Integer pId);
    PostResponse getAllPost(Integer pageNum, Integer pageSize,String sortBy);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer UId);

    List<PostDto> searchPosts(String keyword);

}
