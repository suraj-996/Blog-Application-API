package com.suraj.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suraj.blog.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{
	
}
