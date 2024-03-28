package com.spring.blogs.services;

import com.spring.blogs.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,Integer cId);

    void deleteCategory(Integer cId);

    CategoryDto getCatgoryById(Integer cId);

    List<CategoryDto> getAllCat();
}
