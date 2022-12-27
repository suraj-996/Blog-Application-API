package com.suraj.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.blog.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
