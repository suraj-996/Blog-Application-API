package com.suraj.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.suraj.blog.models.Post;
import com.suraj.blog.payloads.PostDto;
import com.suraj.blog.payloads.PostResponse;
import com.suraj.blog.services.FileService;
import com.suraj.blog.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPostController(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		return new ResponseEntity<PostDto>(postService.createPost(postDto, userId, categoryId),HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePostController(@RequestBody PostDto postDto,@PathVariable Integer postId) {
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, postId),HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<Post> deletePostController(@PathVariable Integer postId) {
		return new ResponseEntity<Post>(postService.deletePost(postId),HttpStatus.OK);
	}
	
	@GetMapping("/getallpost")
	public ResponseEntity<PostResponse> getAllPostController(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir
			){
		return new ResponseEntity<PostResponse>(postService.getAllPost(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
		
	}
	
	@GetMapping("/getpostbyid/{postId}")
	public ResponseEntity<PostDto> getPostByIdController(@PathVariable Integer postId) {
		return new ResponseEntity<PostDto>(postService.getPostById(postId),HttpStatus.OK);
	}
	
	@GetMapping("/getpostbycat/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategoryController(@PathVariable Integer categoryId){
		return new ResponseEntity<List<PostDto>>(postService.getPostByCategory(categoryId),HttpStatus.OK);
	}
	
	@GetMapping("/getpostbyuser/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUserController(@PathVariable Integer userId){
		return new ResponseEntity<List<PostDto>>(postService.getPostByUser(userId),HttpStatus.OK);
	}
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keywords") String keywords){
		return new ResponseEntity<List<PostDto>>(postService.searchPosts(keywords),HttpStatus.OK);
	}
	
	
	@PutMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile file,
			@PathVariable Integer postId) throws IOException{
		PostDto postDto= postService.getPostById(postId);
		String fileName=fileService.uploadImage(path, file);
		
		postDto.setImageName(fileName);
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, postId),HttpStatus.OK);
	}
	
	@GetMapping(value ="/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException{
		InputStream resource=fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
