//package com.wenxt.base.security;
//
//import jakarta.annotation.Nullable;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor; 
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "UserInfo")
//public class UserInfo { 
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//	private int id; 
//	private String name; 
//	private String email; 
//	private String password; 
//	private String roles;
//	@Nullable
//	 private String resetToken;
//	
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getRoles() {
//		return roles;
//	}
//	public void setRoles(String roles) {
//		this.roles = roles;
//	}
//	public String getResetToken() {
//		return resetToken;
//	}
//	public void setResetToken(String resetToken) {
//		this.resetToken = resetToken;
//	} 
//	
//	
//
//} 
//
