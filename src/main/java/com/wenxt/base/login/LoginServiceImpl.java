package com.wenxt.base.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.jasypt.encryption.StringEncryptor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wenxt.base.commonUtils.LOVDTO;
import com.wenxt.base.security.AuthRequest;
import com.wenxt.base.security.JwtService;
import com.wenxt.base.userMaster.LM_MENU_USERS;
import com.wenxt.base.userMaster.UserMasterRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private LoginRepository repo;

//	@Autowired
//	private LoginDao loginDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserMasterRepository userrepo;

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
		String url = getBaseURL + "common/getparamlov?queryId=11&MUCD_COMP_CODE=" + user.getCompanyCode()
				+ "&muc_user_id=" + user.getUserId();
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
		String url = getBaseURL + "common/getparamlov?queryId=12&MUCD_COMP_CODE=" + user.getCompanyCode()
				+ "&muc_user_id=" + user.getUserId() + "&mucd_divn_code=" + user.getBranchCode();
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
//		JSONObject response = new JSONObject();
//		Map<String, Object> data = new HashMap<>();
//
//		AuthRequest auth = new AuthRequest();
//		auth.setUsername(login.getUserName());
//		auth.setPassword(login.getPassword());
//		auth.setDivision(login.getDivisionCode());
//		auth.setDepartment(login.getDepartmentCode());
//		auth.setCompany(login.getCompanyCode());
//		auth.setBaseCurrency("INR");
//		String token = "";
//
//		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(login.getUserName(),
//				login.getPassword());
//		Authentication authentication = authenticationManager.authenticate(authenticationToken);
//		token = jwtService.generateToken(auth);
//		if (authentication.isAuthenticated()) {
//			boolean isFlagSet = checkFlagInMenuTable(authentication); // Check flag in lm_menu table
//			if (isFlagSet) {
//				data.put("Token", token);
//				data.put("URL", "/resetpassword");
//				response.put("Status", "REDIRECT");
//				response.put("status_msg", "Please direct to reset password page");
//				response.put("Data", data);
//				return response.toString();
//			} else {
//
//				if (token != null) {
//					data.put("Token", token);
//					data.put("MenuList", null);
//					response.put("Status", "SUCCESS");
//					response.put("status_msg", "User Logged In Successfully");
//					response.put("Data", data);
//					return response.toString();
//				} else {
//					response.put("Status", "FAILURE");
//					return response.toString();
//				}
//			}
//		} else {
//			response.put("Status", "FAILURE");
//			return response.toString();
//		}
//	}
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
			// Try to authenticate the user
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(login.getUserName(),
					login.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			// Generate JWT token
			token = jwtService.generateToken(auth);

			// Check if authentication is successful
			if (authentication.isAuthenticated()) {
				boolean isFlagSet = checkFlagInMenuTable(authentication); // Check flag in lm_menu table
				if (isFlagSet) {
					data.put("Token", token);
					data.put("URL", "/resetpassword");
					response.put("Status", "REDIRECT");
					response.put("status_msg", "Please direct to reset password page");
					response.put("Data", data);
					return response.toString();
				} else {
					if (token != null) {
						data.put("Token", token);
						data.put("MenuList", null);
						response.put("Status", "SUCCESS");
						response.put("status_msg", "User Logged In Successfully");
						response.put("Data", data);
						return response.toString();
					} else {
						response.put("Status", "FAILURE");
						return response.toString();
					}
				}
			} else {
				response.put("Status", "FAILURE");
				return response.toString();
			}
		} catch (BadCredentialsException e) {
			// Handle incorrect password
			response.put("Status", "FAILURE");
			response.put("Message", "Incorrect password");
			return response.toString();
		} catch (UsernameNotFoundException e) {
			// Handle username not found
			response.put("Status", "FAILURE");
			response.put("Message", "Username not found");
			return response.toString();
		}
	}

	private boolean checkFlagInMenuTable(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			// Retrieve the username of the logged-in user
			String username = authentication.getName();

			// Assuming you have access to the repository and a method to find the user by
			// username
			LM_MENU_USERS menuUser = userrepo.findByUserIdAndUserFirstLoginyn(username, true);

			// Check if menuUser is not null, indicating the flag is set for the logged-in
			// user
			return menuUser != null;
		}
		return false;
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

		for (LOVDTO language : list) {
			if (language.getValue().equals("English")) {
				languageResponse = new JSONObject();
				languageResponse.put("label", language.getLabel());
				languageResponse.put("value", language.getValue());
				languageResponse.put("IsDefault", "True");
				data.add(languageResponse);
			} else {
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
	public String resetProfilePassword(String password, String newPassword, String confirmPassword) {
		JSONObject response = new JSONObject();

		if (!newPassword.equals(confirmPassword)) {
			return "New password and confirm password do not match!";
		}

		// Retrieve the username from JWT token
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String jwtuser = authentication.getName();

		Optional<LM_MENU_USERS> userOptional = userrepo.findByUserId(jwtuser);

		if (userOptional.isPresent()) {
			LM_MENU_USERS user = userOptional.get();

			String hashedPassword = user.getUser_passwd();

			String decryptedPassword = encryptor.decrypt(hashedPassword);
			if (password.equals(decryptedPassword)) {
				// Encode and set the new password
				user.setUser_passwd(encryptor.encrypt(newPassword));
				user.setUserFirstLoginyn(false);
				userrepo.save(user);
				response.put("Status", "SUCCESS");
				response.put("status_msg", "Password reset successful");

				return response.toString();
			} else {
				response.put("Status", "FAILURE");
				response.put("status_msg", "Invalid password!");
				return response.toString();
			}
		} else {
			response.put("Status", "FAILURE");
			response.put("status_msg", "User not found!");
			return response.toString();
		}
	}

	@Override
	public ResponseEntity<String> processForgotPassword(String email) {
		JSONObject response = new JSONObject();
		try {
			LM_MENU_USERS user = userrepo.findByUserEmailId(email);
			if (user == null) {
				response.put("Status", "FAILURE");
				response.put("Message", "User not found");

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
			}

			String token = UUID.randomUUID().toString();
			user.setUserResettoken(token);
			userrepo.save(user);
			response.put("Status", "SUCCESS");
			response.put("Message", "Reset password email sent successfully");

			sendResetPasswordEmail(user.getUserEmailId(), token);

			return ResponseEntity.ok(response.toString());
		} catch (Exception e) {
			response.put("Status", "FAILURE");
			response.put("Message", "Failed to reset password: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
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
	public ResponseEntity<String> resetPassword(String token, String newPassword, String confirmPassword)
			throws InvalidTokenException {
		JSONObject response = new JSONObject();
		try {
			LM_MENU_USERS user = userrepo.findByUserResettoken(token);
			if (user != null) {
				// Update user's password and reset token
				user.setUser_passwd(encryptor.encrypt(newPassword));
				user.setUserResettoken(null);

				userrepo.save(user);

				// Generate new token
				String newToken = UUID.randomUUID().toString();

				response.put("Status", "SUCCESS");
				response.put("Message", "password reset sucessfully");
				return ResponseEntity.ok(response.toString());
			} else {
				// Invalid token
				response.put("Status", "FAILURE");
				response.put("Message", "Invalid token");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
			}
		} catch (Exception e) {
			// Exception occurred
			response.put("Status", "FAILURE");
			response.put("Message", "Failed to reset password: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
	}

}
