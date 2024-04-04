package com.wenxt.base.userMaster;

public interface UserMasterService {
	
	public String createUser(LM_MENU_USERS lm_menu_user);
	
	public String getAllUserlist();
	
	public String deleteUserById(String userId);
	
	public String getUserById(String userId);

}
