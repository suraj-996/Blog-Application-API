package com.suraj.blog.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commentId;
	private String content;
	@ManyToOne()
	private Post post;
}
