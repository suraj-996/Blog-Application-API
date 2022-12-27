package com.suraj.blog.services;

import com.suraj.blog.payloads.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto,Integer postId);
	public CommentDto deleteComment(Integer commentId);
}
