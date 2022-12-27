package com.suraj.blog.payloads;

import lombok.Data;

@Data
public class CommentDto {
	private Integer commentId;
	private String content;
}
