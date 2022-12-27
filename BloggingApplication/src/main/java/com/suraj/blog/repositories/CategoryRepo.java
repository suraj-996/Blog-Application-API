package com.suraj.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.blog.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
