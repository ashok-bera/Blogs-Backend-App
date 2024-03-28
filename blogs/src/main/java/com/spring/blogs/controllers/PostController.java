package com.spring.blogs.controllers;

import com.spring.blogs.config.AppConstants;
import com.spring.blogs.dtos.ApiResponse;
import com.spring.blogs.dtos.PostDto;
import com.spring.blogs.dtos.PostResponse;
import com.spring.blogs.services.FileService;
import com.spring.blogs.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("user/{userId}/category/{Cid}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer Cid){
        PostDto postDto1 = this.postService.createPost(postDto,userId,Cid);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1 = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }
    //get posts by user
    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postDtoList = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }


    //get posts by category
    @GetMapping("category/{Cid}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer Cid){
        List<PostDto> postDtoList = this.postService.getPostsByCategory(Cid);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNum",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy){
        PostResponse postResponse = this.postService.getAllPost(pageNum,pageSize,sortBy);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("post removed",true);
    }

    //search
    @GetMapping("posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keywords") String keywords){
        List<PostDto> postDtoList = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    //upload image
    @PostMapping("posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                   @PathVariable Integer postId) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.CREATED);
    }

    @GetMapping(value = "posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
