package com.wenxt.base.emailSample.service;

import org.json.JSONObject;

public interface EmailService {
	
	public void sendMail(String mail, JSONObject emailStructure);
	
//	public String convertToMultiPartFile(File document);

}
