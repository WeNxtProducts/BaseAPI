package com.wenxt.base.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jasypt.encryption.StringEncryptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wenxt.base.commonUtils.LOVDTO;
import com.wenxt.base.security.AuthRequest;
import com.wenxt.base.security.JwtService;
import com.wenxt.base.security.UserInfo;
import com.wenxt.base.security.UserInfoRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private KieContainer kieContainer;
	
	@Autowired
	private LoginRepository repo;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoRepository repository;
 
    @Autowired
    private StringEncryptor encryptor;
    	
	@Autowired
    private JavaMailSender javaMailSender;

	@Value("${get.company.message}")
	private String getCompanyMessage;
	
	@Value("${get.language.message}")
	private String getLanguageMessage;

	@Value("${get.branch.message}")
	private String getBranchMessage;

	@Value("${get.department.message}")
	private String getDepartmentMessage;
	
	@Value("${user.login.message}")
	private String loginMessage;
	
	@Value("${reset.password.token}")
	private String newToken;
	
	@Value("${reset.password.mail}")
	private String resetPasswordMail;
	
	@Value("${reset.password.message}")
	private String resetPassword;
	
	@Value("${spring.project.baseUrl}")
	private String getBaseURL;
	
	@Value("${spring.success.code}")
	private String successCode;

	@Value("${spring.error.code}")
	private String errorCode;

	@Value("${spring.warning.code}")
	private String warningCode;

	@Value("${spring.message.code}")
	private String messageCode;

	@Value("${spring.status.code}")
	private String statusCode;

	@Value("${spring.data.code}")
	private String dataCode;
	
	@Override
	public String getCompany(LoginDropDownRequestModel user) {

		JSONObject response = new JSONObject();
		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + "common/getparamlov?queryId=10&muc_user_id=" + user.getUserId();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String serviceResponse = responseEntity.getBody();
		} else {
			throw new UsernameNotFoundException("JUST CHECK");
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(responseEntity.getBody().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = (JSONArray) jsonObject.get("Data");
		List<LOVDTO> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			LOVDTO model = new LOVDTO(obj.get("label"), obj.get("value")); // Assuming model structure
			list.add(model);
		}

		user.setCompanyCodes(list);
		KieSession kieSession = kieContainer.newKieSession();

		try {
			kieSession.insert(user);
			kieSession.fireAllRules();
		} finally {
			kieSession.dispose();
		}

		if (user.getErrorMessage() == null) {
			response.put(statusCode, successCode);
			response.put(messageCode, getCompanyMessage);
			response.put(dataCode, list);
			return response.toString();
		} else {
			List<Map<String, String>> errorList = new ArrayList<>();
			Map<String, String> errorMap = new LinkedHashMap<String, String>();
			response.put(statusCode, errorCode);
			response.put(messageCode, "Validation Error");
			errorMap.put("Field", user.getErrorField());
			errorMap.put(messageCode, user.getErrorMessage());
			errorList.add(errorMap);
			response.put("Error", errorList);
			return response.toString();
		}
	}

	@Override
	public String getBranch(LoginDropDownRequestModel user) {
		JSONObject response = new JSONObject();

		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + "common/getparamlov?queryId=11&MUCD_COMP_CODE=" + user.getCompanyCode() + "&muc_user_id=" + user.getUserId();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String serviceResponse = responseEntity.getBody();
		} else {
			throw new UsernameNotFoundException("JUST CHECK");
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(responseEntity.getBody().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = (JSONArray) jsonObject.get("Data");
		List<LOVDTO> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			LOVDTO model = new LOVDTO(obj.get("label"), obj.get("value")); // Assuming model structure
			list.add(model);
		}
		response.put(statusCode, successCode);
		response.put(messageCode, getBranchMessage);
		response.put(dataCode, list);
		return response.toString();
	}

	@Override
	public String getDept(LoginDropDownRequestModel user) {
		JSONObject response = new JSONObject();

//		List<KeyValueModel> list = loginDao.getDept(user.getUserId(), user.getCompanyCode(), user.getBranchCode());
		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + "common/getparamlov?queryId=12&MUCD_COMP_CODE=" + user.getCompanyCode() + "&muc_user_id=" + user.getUserId() + "&mucd_divn_code=" + user.getBranchCode();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String serviceResponse = responseEntity.getBody();
		} else {
			throw new UsernameNotFoundException("JUST CHECK");
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(responseEntity.getBody().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = (JSONArray) jsonObject.get("Data");
		List<LOVDTO> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			LOVDTO model = new LOVDTO(obj.get("label"), obj.get("value")); // Assuming model structure
			list.add(model);
		}
		response.put(statusCode, successCode);
		response.put(messageCode, getDepartmentMessage);
		response.put(dataCode, list);
		return response.toString();
	}

	@Override
	public String login(LoginRequestModel login) {
		JSONObject response = new JSONObject();
		Map<String, Object> data = new HashMap<>();
		
		AuthRequest auth = new AuthRequest();
		auth.setUsername(login.getUserName());
		auth.setPassword(login.getPassword());
		auth.setDivision(login.getDivisionCode());
		auth.setDepartment(login.getDepartmentCode());
		auth.setCompany(login.getCompanyCode());
		auth.setBaseCurrency("INR");
		String token = "";
		try {
		 Authentication authenticationToken =
		            new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenticationToken); 
			if (authentication.isAuthenticated()) {
				token = jwtService.generateToken(auth); 
				if(token != null) {
					data.put("Token", token);
					data.put("MenuList", null);
					response.put(statusCode, successCode);
					response.put(messageCode, loginMessage);
					response.put(dataCode, data);
					return response.toString();
				}
				else {
					response.put(statusCode, errorCode);
					return response.toString();
				}
			} else {
				response.put(statusCode, errorCode);
				return response.toString();
			} 
			}catch(Exception e) {
				response.put(statusCode, errorCode);
				response.put(messageCode, e.getMessage());
				return response.toString();
			}
			
		}

	@Override
	public String getLang() {
		JSONObject response = new JSONObject();
//		List<KeyValueModel> languages = loginDao.getLang();
		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + "common/getparamlov?queryId=13";
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String serviceResponse = responseEntity.getBody();
		} else {
			throw new UsernameNotFoundException("JUST CHECK");
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(responseEntity.getBody().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = (JSONArray) jsonObject.get("Data");
		List<LOVDTO> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			LOVDTO model = new LOVDTO(obj.get("label"), obj.get("value")); // Assuming model structure
			list.add(model);
		}
		List<JSONObject> data = new ArrayList<>();
		JSONObject languageResponse = new JSONObject();
		
		response.put(statusCode, successCode);
		response.put(messageCode, getLanguageMessage);
		
		for(LOVDTO language : list) {
			if(language.getValue().equals("English")) {
				languageResponse = new JSONObject();
				languageResponse.put("label", language.getLabel());
				languageResponse.put("value", language.getValue());
				languageResponse.put("IsDefault", "True");
				data.add(languageResponse);
			}else {
				languageResponse = new JSONObject();
				languageResponse.put("label", language.getLabel());
				languageResponse.put("value", language.getValue());
				languageResponse.put("IsDefault", "False");
				data.add(languageResponse);
			}
		}
		response.put(dataCode, data);
		return response.toString();
	}
	
	@Override
	public String resetPassword(String name, String password, String newPassword, String confirmPassword) {
		JSONObject response = new JSONObject();
		if (!newPassword.equals(confirmPassword)) {
			return "New password and confirm password do not match!";
		}

		Optional<UserInfo> userOptional = repository.findByCustommName(name);

		if (userOptional.isPresent()) {
			UserInfo user = userOptional.get();

			String hashedPassword = user.getPassword();

			String decryptedPassword = encryptor.decrypt(hashedPassword);
			if (password.equals(decryptedPassword)) {
				// Encode and set the new password
				user.setPassword(encryptor.encrypt(newPassword));
				repository.save(user);
				response.put(statusCode, successCode);
				response.put(messageCode, resetPassword);
				
				return response.toString();
			} else {
				return "Invalid password!";
			}
		} else {
			return "User not found!";
		}
	}

	@Override
	public String processForgotPassword(String email) throws Exception {
		JSONObject response = new JSONObject();
		UserInfo user = repository.findByEmail(email);
		if (user == null) {
			throw new Exception("User not found");
		}

		String token = UUID.randomUUID().toString();
		user.setResetToken(token);
		repository.save(user);
		response.put(statusCode, successCode);
		response.put(messageCode, resetPasswordMail);

		sendResetPasswordEmail(user.getEmail(), token);
		
		return response.toString();
	}

	private void sendResetPasswordEmail(String email, String token) {
		String resetPasswordLink = "http://localhost:8080/reset-password?token=" + token;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Password Reset Request");
		message.setText("To reset your password, please click on the following link: " + resetPasswordLink);

		javaMailSender.send(message);
	}

	@Override
	public String resetPassword(String token, String newPassword) throws InvalidTokenException {
		JSONObject response = new JSONObject();
		UserInfo user = repository.findByResetToken(token);
		if (user != null) {

			user.setPassword(encryptor.encrypt(newPassword));

			user.setResetToken(null);

			repository.save(user);
			
			response.put(statusCode, successCode);
			response.put(messageCode, newToken);
			return response.toString();
		} else {
			throw new InvalidTokenException("Invalid token");
		}
	}

}
