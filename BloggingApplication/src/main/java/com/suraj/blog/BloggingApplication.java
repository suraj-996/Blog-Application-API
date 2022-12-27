package com.suraj.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.suraj.blog.models.Role;
import com.suraj.blog.repositories.RoleRepo;


@SpringBootApplication
public class BloggingApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("xyz"));
		try {
			Role role1=new Role();
			role1.setId(501);
			role1.setName("ADMIN_USER");
			
			Role role2=new Role();
			role2.setId(502);
			role2.setName("NORMAL_USER");
			
			List<Role> roles= List.of(role1,role2);
			List<Role> result=roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
