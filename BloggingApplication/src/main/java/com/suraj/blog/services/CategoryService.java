package com.suraj.blog.services;

import java.util.List;

import com.suraj.blog.payloads.CategoryDto;

public interface CategoryService {
	public CategoryDto creageCategory(CategoryDto categoryDto);
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	public CategoryDto deleteCategory(Integer categoryId);
	public CategoryDto getCategory(Integer categoryId);
	public List<CategoryDto> getAllCategories();
}
