package com.spring.blogs.services.impl;

import com.spring.blogs.dtos.CategoryDto;
import com.spring.blogs.dtos.UserDto;
import com.spring.blogs.entities.Category;
import com.spring.blogs.exceptions.ResourceNotFoundException;
import com.spring.blogs.repositories.CategoryRepo;
import com.spring.blogs.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category category = this.dtoToCategory(categoryDto);
       Category savedCategory = this.categoryRepo.save(category);
       return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer cId) {

        Category category = this.categoryRepo.findById(cId).orElseThrow(()->new ResourceNotFoundException("Category","Id",cId));
//        category.setCId(categoryDto.getCId());
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        this.categoryRepo.save(category);
        return this.categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer cId) {
        Category category = this.categoryRepo.findById(cId).orElseThrow(()->new ResourceNotFoundException("Category","Id",cId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCatgoryById(Integer cId) {
        Category category = this.categoryRepo.findById(cId).orElseThrow(()->new ResourceNotFoundException("Category","Id",cId));
        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCat() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> dtoList = categories.stream().map(category -> categoryToDto(category)).collect(Collectors.toList());
        return dtoList;

    }

    private CategoryDto categoryToDto(Category category)
    {
        return this.modelMapper.map(category,CategoryDto.class);
    }

    private Category dtoToCategory(CategoryDto categoryDto)
    {
        return this.modelMapper.map(categoryDto,Category.class);
    }

}
