package com.spring.blogs.services.impl;

import com.spring.blogs.dtos.PostDto;
import com.spring.blogs.dtos.PostResponse;
import com.spring.blogs.entities.Category;
import com.spring.blogs.entities.Post;
import com.spring.blogs.entities.User;
import com.spring.blogs.exceptions.ResourceNotFoundException;
import com.spring.blogs.repositories.CategoryRepo;
import com.spring.blogs.repositories.PostRepo;
import com.spring.blogs.repositories.UserRepo;
import com.spring.blogs.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer Uid, Integer categoryId) {
        User user = this.userRepo.findById(Uid).orElseThrow(()-> new ResourceNotFoundException("USer","Id",Uid));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = this.postRepo.save(post);
        return postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer pId) {
        Post post = this.postRepo.findById(pId).orElseThrow(()-> new ResourceNotFoundException("Post","pId",pId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.postToDto(updatedPost);
    }

    @Override
    public void deletePost(Integer pId) {
        Post post = this.postRepo.findById(pId).orElseThrow(()-> new ResourceNotFoundException("Post","pId",pId));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer pId) {
        Post post = this.postRepo.findById(pId).orElseThrow(()-> new ResourceNotFoundException("Post","pId",pId));
        return this.postToDto(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNum, Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNum,pageSize, Sort.by(sortBy).ascending());
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> postList = pagePost.getContent();
        List<PostDto> postDtos = postList.stream().map(post -> postToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPosts(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer UId) {
        User user = this.userRepo.findById(UId).orElseThrow(()-> new ResourceNotFoundException("User","Id",UId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> postList = this.postRepo.findByTitleContaining(keyword);
        System.out.println("post__"+postList);
        List<PostDto> postDtoList = postList.stream().map(post->postToDto(post)).collect(Collectors.toList());
        return postDtoList;
    }

    public PostDto postToDto(Post post){
        return this.modelMapper.map(post,PostDto.class);
    }

    public Post dtoToPost(PostDto postDto){
        return this.modelMapper.map(postDto,Post.class);
    }
}
