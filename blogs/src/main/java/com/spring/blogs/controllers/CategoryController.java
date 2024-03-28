package com.spring.blogs.controllers;

import com.spring.blogs.dtos.ApiResponse;
import com.spring.blogs.dtos.CategoryDto;
import com.spring.blogs.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> cretaeCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }
    @PutMapping("/{cId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("cId") Integer cid)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,cid);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{cId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("cId") Integer cid)
    {
        this.categoryService.deleteCategory(cid);
        //return new ResponseEntity<>(Map.of("message","user deleted successfully"),HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse("Category removed Successfully",true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCat()
    {
        List<CategoryDto> categoryDtos = this.categoryService.getAllCat();
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }
    @GetMapping("/{cID}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("cID") Integer cid)
    {
        CategoryDto categoryDto  = this.categoryService.getCatgoryById(cid);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
}
