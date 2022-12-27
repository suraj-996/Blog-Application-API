package com.suraj.blog.payloads;

import java.util.List;

import lombok.Data;

@Data
public class PostResponse {
	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private boolean lastPage;
	
}
