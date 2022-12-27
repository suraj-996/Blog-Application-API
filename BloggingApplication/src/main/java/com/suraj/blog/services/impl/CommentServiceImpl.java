package com.suraj.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suraj.blog.exceptions.ResourceNotFoundException;
import com.suraj.blog.models.Comment;
import com.suraj.blog.models.Post;
import com.suraj.blog.payloads.CommentDto;
import com.suraj.blog.repositories.CommentRepo;
import com.suraj.blog.repositories.PostRepo;
import com.suraj.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id ", postId));
		Comment comment=modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment=commentRepo.save(comment);
		return modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public CommentDto deleteComment(Integer commentId) {
		Comment comment=commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment ", " Comment id ", commentId));
		commentRepo.delete(comment);
		return modelMapper.map(comment, CommentDto.class);
		
	}

}
