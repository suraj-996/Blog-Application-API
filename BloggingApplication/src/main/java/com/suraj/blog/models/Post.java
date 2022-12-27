package com.suraj.blog.models;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.suraj.blog.payloads.CommentDto;

import lombok.Data;

@Entity
@Data
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;
	private String title;
	@Column(length = 1000)
	private String content;
	private String imageName;
	private Date addedDate;
	
	@ManyToOne()
	private Category category;
	@ManyToOne()
	private User user;
	
	@OneToMany(mappedBy ="post",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private Set<Comment> comments=new HashSet<>();
}
