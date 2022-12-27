package com.suraj.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.blog.payloads.CategoryDto;
import com.suraj.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> creageCategoryController(@Valid @RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<CategoryDto>(categoryService.creageCategory(categoryDto),HttpStatus.CREATED);
	}
	@PutMapping("/upadate/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategoryController(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
		return new ResponseEntity<CategoryDto>(categoryService.updateCategory(categoryDto, categoryId),HttpStatus.OK);
	}
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<CategoryDto> deleteCategoryController(@PathVariable Integer categoryId) {
		return new ResponseEntity<CategoryDto>(categoryService.deleteCategory(categoryId),HttpStatus.OK);
	}
	@GetMapping("/getcategory/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryController(@PathVariable Integer categoryId) {
		return new ResponseEntity<CategoryDto>(categoryService.getCategory(categoryId),HttpStatus.OK);
	}
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategoriesController(){
		return new ResponseEntity<List<CategoryDto>>(categoryService.getAllCategories(),HttpStatus.OK);
	}
}
