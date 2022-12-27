package com.suraj.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suraj.blog.exceptions.ResourceNotFoundException;
import com.suraj.blog.models.Category;
import com.suraj.blog.payloads.CategoryDto;
import com.suraj.blog.repositories.CategoryRepo;
import com.suraj.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto creageCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory=categoryRepo.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category= categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", " Category Id ", categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(category.getCategoryTitle());
		Category cat= categoryRepo.save(category);
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public CategoryDto deleteCategory(Integer categoryId) {
		Category category= categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", " Category Id ", categoryId));
		categoryRepo.delete(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category= categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", " Category Id ", categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories=categoryRepo.findAll();
		List<CategoryDto> ans= categories.stream().map((c)->modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
		return ans;
	}

}
