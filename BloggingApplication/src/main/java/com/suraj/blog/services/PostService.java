package com.suraj.blog.services;

import java.util.List;

import com.suraj.blog.models.Post;
import com.suraj.blog.payloads.PostDto;
import com.suraj.blog.payloads.PostResponse;

public interface PostService {
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	public PostDto updatePost(PostDto postDto,Integer postId);
	public Post deletePost(Integer postId);
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	public PostDto getPostById(Integer postId);
	public List<PostDto> getPostByCategory(Integer categoryId);
	public List<PostDto> getPostByUser(Integer userId);
	public List<PostDto> searchPosts(String keyword);
}
