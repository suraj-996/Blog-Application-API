package com.suraj.blog.services.impl;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.suraj.blog.exceptions.ResourceNotFoundException;
import com.suraj.blog.models.Category;
import com.suraj.blog.models.Post;
import com.suraj.blog.models.User;
import com.suraj.blog.payloads.PostDto;
import com.suraj.blog.payloads.PostResponse;
import com.suraj.blog.repositories.CategoryRepo;
import com.suraj.blog.repositories.PostRepo;
import com.suraj.blog.repositories.UserRepo;
import com.suraj.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User id", userId));
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", " category id ", categoryId));
		Post post=modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post newPost=postRepo.save(post);
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", " post id ", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost=postRepo.save(post);
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public Post deletePost(Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", " post id ", postId));
		postRepo.delete(post);
		return post;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pa=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=postRepo.findAll(pa);
		List<Post> posts= pagePost.getContent();
		List<PostDto> postDtos= posts.stream().map((p)->modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", " post id ", postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", " category id ", categoryId));
		List<Post> posts=postRepo.findByCategory(category);
		List<PostDto> postDtos= posts.stream().map((p)->modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User id", userId));
		List<Post> posts=postRepo.findByUser(user);
		List<PostDto> postDtos=posts.stream().map((p)->modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts= postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos= posts.stream().map((p)->modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	

}
