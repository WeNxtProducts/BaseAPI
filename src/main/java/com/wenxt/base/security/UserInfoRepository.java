package com.wenxt.base.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; 

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> { 
	
    @Query("SELECT e FROM UserInfo e WHERE e.name = :input")
	Optional<UserInfo> findByCustommName(@Param("input") String input); 
		 
	UserInfo findByEmail(String email);
 
	UserInfo findByResetToken(String token);
}

