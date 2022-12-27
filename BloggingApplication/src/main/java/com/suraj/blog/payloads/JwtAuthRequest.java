package com.suraj.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	private String username;
	private String password;
}
