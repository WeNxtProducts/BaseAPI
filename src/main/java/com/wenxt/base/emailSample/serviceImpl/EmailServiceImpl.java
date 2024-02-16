package com.wenxt.base.emailSample.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.wenxt.base.emailSample.dao.EmailDao;
import com.wenxt.base.emailSample.model.BASE64DecodedMultipartFile;
import com.wenxt.base.emailSample.service.EmailService;
import com.wenxt.base.sample.SampleRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private EmailDao emailDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SampleRepository sampRepo;
	
	@Value("$(spring.mail.username)")
	private String fromMail;

	@Override
	public void sendMail(String mail, JSONObject emailStructure) {

		Map<String, BASE64DecodedMultipartFile> multiPartList = new HashMap<>();
		
		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					if (emailStructure.has("to")) {
						mimeMessageHelper.setTo(emailStructure.getString("to"));
					}
					if (emailStructure.has("cc")) {
						String[] ccAddressArray = emailStructure.getString("cc").split(",");
						mimeMessageHelper.setCc(ccAddressArray);
					}
					if (emailStructure.has("bcc")) {
						mimeMessageHelper.setBcc(emailStructure.getString("bcc"));
					}
					if (emailStructure.has("subject")) {
						mimeMessageHelper.setSubject(emailStructure.getString("subject"));
					}
					if (emailStructure.has("message")) {
						mimeMessageHelper.setText(emailStructure.getString("message"));
					}

					if (emailStructure.has("attachment")) {
						JSONObject tempAttachementJson = (JSONObject) emailStructure.get("attachment");
						Set<String> keys = tempAttachementJson.keySet();
						for (String key : keys) {
							BASE64DecodedMultipartFile conv = new BASE64DecodedMultipartFile(
									(byte[]) tempAttachementJson.get(key));
							multiPartList.put(key, conv);
						}
					}

					Set<String> fileName = multiPartList.keySet();
					for (String filename : fileName) {
						mimeMessageHelper.addAttachment(filename, new InputStreamSource() {
							
							@Override
							public InputStream getInputStream() throws IOException {
								return multiPartList.get(filename).getInputStream();
							}
						});
					}

				} catch (final Exception exception) {
					exception.printStackTrace();
				}
			}

		});
		
	}
//	@Override
//	public String convertToMultiPartFile(File document) {
//		 byte[] documentBytes;
////		try {
////			documentBytes = Files.readAllBytes(document.toPath());
////
////
////	        // Create a ByteArrayResource
////	        ByteArrayResource resource = new ByteArrayResource(documentBytes) {
////	            @Override
////	            public String getFilename() {
////	                return document.getName();
////	            }
////	        };
////
////	        // Convert ByteArrayResource to MultipartFile
////	        MultipartFile multipartFile;
////				multipartFile = new MockMultipartFile(
////				        document.getName(),
////				        document.getName(),
////				        "text/plain",
////				        resource.getInputStream()
////				);
////		        return multipartFile;
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		 try {
//			 System.out.println("IN");
////			MultipartFile multipartFile = new MockMultipartFile("dependencies.txt", new FileInputStream(new File("D:\\Documents\\Dependency Document\\dependencies.txt")));
////			return multipartFile;
//			
//			return Base64.getEncoder().encodeToString(Files.readAllBytes(document.toPath()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//
//	    }

}
