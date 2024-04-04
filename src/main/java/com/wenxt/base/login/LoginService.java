package com.wenxt.base.login;

public interface LoginService {
	
	public String getCompany(LoginDropDownRequestModel user);
	
	public String getBranch(LoginDropDownRequestModel user);
	
	public String getDept(LoginDropDownRequestModel user);
	
	public String login(LoginRequestModel login);
	
	public String getLang();

	public String resetPassword(String name, String password, String newPassword, String confirmPassword);
	
	public String processForgotPassword(String email) throws Exception;
	
	public String resetPassword(String token, String newPassword) throws InvalidTokenException;
}
