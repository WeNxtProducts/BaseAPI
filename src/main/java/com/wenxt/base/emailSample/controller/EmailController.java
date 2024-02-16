package com.wenxt.base.emailSample.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wenxt.base.emailSample.model.EmailStructure;
import com.wenxt.base.emailSample.service.EmailService;

@RestController
@RequestMapping("/mail")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/send/{mail}")
	public String sendMail(@PathVariable String mail, @RequestBody EmailStructure emailStructure) {
		System.out.println("IN CONTROLLER");
		JSONObject emailJson = new JSONObject();
		emailJson.put("subject", emailStructure.getSubject());
		emailJson.put("message", emailStructure.getMessage());
		emailJson.put("to", mail);
		emailJson.put("cc", emailStructure.getCc());
		emailJson.put("bcc", emailStructure.getBcc());
		emailService.sendMail(mail, emailJson);
		return "Mail Successfully Sent";
	}
	
	@PostMapping("/sendWithAtt/{mail}")
	public String sendMailWithAtt(@PathVariable String mail, @RequestBody EmailStructure emailStructure) throws IOException {
		byte[] attachement1 = Files.readAllBytes(Paths.get("D:\\Documents\\Dependency Document\\dependencies.txt"));
		byte[] attachment2 = Files.readAllBytes(Paths.get("D:\\Documents\\Dependency Document\\Important.txt"));
		JSONObject attachmentJson = new JSONObject();
		
		attachmentJson.put(Paths.get("D:\\Documents\\Dependency Document\\dependencies.txt").getFileName().toString(), attachement1);
		attachmentJson.put(Paths.get("D:\\Documents\\Dependency Document\\Important.txt").getFileName().toString(), attachment2);
	      
	      JSONObject emailJson = new JSONObject();
			emailJson.put("subject", emailStructure.getSubject());
			emailJson.put("message", emailStructure.getMessage());
			emailJson.put("to", mail);
			emailJson.put("cc", emailStructure.getCc());
			emailJson.put("bcc", emailStructure.getBcc());
			emailJson.put("attachment", attachmentJson);
			
			emailService.sendMail(mail, emailJson);
	      
	      return "SUCCESS";
	}

}
