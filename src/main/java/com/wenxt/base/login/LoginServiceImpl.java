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
import org.springframework.jdbc.core.JdbcTemplate;
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
	
	@Autowired
	private JdbcTemplate template;
	
	

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
			response.put("status_msg", "Incorrect password");
			return response.toString();
		} catch (UsernameNotFoundException e) {
			// Handle username not found
			response.put("Status", "FAILURE");
			response.put("status_msg", "Username not found");
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
				response.put("status_msg", "User not found");

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
			}

			String token = UUID.randomUUID().toString();
			user.setUserResettoken(token);
			userrepo.save(user);
			response.put("Status", "SUCCESS");
			response.put("status_msg", "Reset password email sent successfully");

			sendResetPasswordEmail(user.getUserEmailId(), token);

			return ResponseEntity.ok(response.toString());
		} catch (Exception e) {
			response.put("Status", "FAILURE");
			response.put("status_msg", "Failed to reset password: " + e.getMessage());
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
				response.put("status_msg", "password reset sucessfully");
				return ResponseEntity.ok(response.toString());
			} else {
				// Invalid token
				response.put("Status", "FAILURE");
				response.put("status_msg", "Invalid token");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
			}
		} catch (Exception e) {
			// Exception occurred
			response.put("Status", "FAILURE");
			response.put("status_msg", "Failed to reset password: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
		}
	}

	@Override
	public String password(String username, String password, String userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		JSONObject response = new JSONObject();
		Map<String, Object> data = new HashMap<>();

		if (authentication != null && authentication.isAuthenticated()) {
			String jwtUser = authentication.getName();

			if ("ADMIN".equals(username) && "ADMIN".equals(jwtUser)) {
				// Assuming userrepo is a repository for user data
				List<LM_MENU_USERS> userList = userrepo.findAll();

				// Find the user by userId
				Optional<LM_MENU_USERS> userOptional = userList.stream().filter(user -> userId.equals(user.getUserId()))
						.findFirst();

				if (userOptional.isPresent()) {
					LM_MENU_USERS user = userOptional.get();
					String encryptedPassword = user.getUser_passwd();
					String decryptedPassword = encryptor.decrypt(encryptedPassword);

					// Verify admin password
					if (password.equals("Test@123")) {
						data.put("UserID", user.getUserId());
						data.put("Password", decryptedPassword);
						response.put("Status", "SUCCESS");
						response.put("status_msg", "Password retrieved successfully");
						response.put("Data", data);
					} else {
						response.put("Status", "FAILURE");
						response.put("status_msg", "Invalid admin password");
					}
				} else {
					response.put("Status", "FAILURE");
					response.put("status_msg", "User not found");
				}
			} else {
				response.put("Status", "FAILURE");
				response.put("status_msg", "Invalid user");
			}
		} else {
			response.put("Status", "FAILURE");
			response.put("status_msg", "User not authenticated");
		}

		return response.toString();
	}

	@Override
	public String getAllcompanyListByuser(LoginDropDownRequestModel user) {

		JSONObject response = new JSONObject();
		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + "common/getparamlov?queryId=19&muc_user_id=" + user.getUserId();
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
	public String getAllDeptListByUser(LoginDropDownRequestModel user) {
	    JSONObject response = new JSONObject();
	    RestTemplate restTemplate = new RestTemplate();
	    String baseURL = getBaseURL + "common/getparamlov?queryId=";

	    try {
	        // Fetching country list
	        String url = baseURL + "19" + "&muc_user_id=" + user.getUserId();

	        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
	        if (responseEntity.getStatusCode() == HttpStatus.OK) {
	            String serviceResponse = responseEntity.getBody();
	            JSONArray jsonArray = null;
	            try {
	                jsonArray = new JSONObject(serviceResponse).getJSONArray("Data");
	            } catch (JSONException e) {
	                throw new UsernameNotFoundException("Data not found in the response");
	            }
	            List<LOVDTO> countryList = parseLOVDTOList(jsonArray);
	            user.setCompanyListCodes(countryList);
	        } else {
	            throw new UsernameNotFoundException("Error fetching country list");
	        }

	        // Fetching branch list
//	        url = baseURL + "20" + "MUCD_COMP_CODE=" + user.getCompanyCode();
	        
	        url = baseURL + "20" + "&MUCD_COMP_CODE=" + user.getCompanyCode();

	        responseEntity = restTemplate.getForEntity(url, String.class);
	        if (responseEntity.getStatusCode() == HttpStatus.OK) {
	            String serviceResponse = responseEntity.getBody();
	            JSONArray jsonArray = null;
	            try {
	                jsonArray = new JSONObject(serviceResponse).getJSONArray("Data");
	            } catch (JSONException e) {
	                throw new UsernameNotFoundException("Data not found in the response");
	            }
	            List<LOVDTO> branchList = parseLOVDTOList(jsonArray);
	            user.setBranchListCodes(branchList);
	        } else {
	            throw new UsernameNotFoundException("Error fetching branch list");
	        }

	        // Fetching department list
	        url = baseURL + "20" + "&MUCD_COMP_CODE=" + user.getCompanyCode();

	        responseEntity = restTemplate.getForEntity(url, String.class);
	        if (responseEntity.getStatusCode() == HttpStatus.OK) {
	            String serviceResponse = responseEntity.getBody();
	            JSONArray jsonArray = null;
	            try {
	                jsonArray = new JSONObject(serviceResponse).getJSONArray("Data");
	            } catch (JSONException e) {
	                throw new UsernameNotFoundException("Data not found in the response");
	            }
	            List<LOVDTO> departmentList = parseLOVDTOList(jsonArray);
	            user.setDeptListCodes(departmentList);
	        } else {
	            throw new UsernameNotFoundException("Error fetching department list");
	        }

	        // Response construction
	        response.put("Status", "SUCCESS");
	        response.put("status_msg", "getCompanyMessage");
	        response.put("companyList", user.getCompanyListCodes());
	        response.put("branchList", user.getBranchListCodes());
	        response.put("departmentList", user.getDeptListCodes());

	    } catch (Exception e) {
	        response.put("Status", "errorCode");
	        response.put("status_msg", "Error occurred");
	        response.put("error", e.getMessage());
	    }

	    return response.toString();
	}

	private List<LOVDTO> parseLOVDTOList(JSONArray jsonArray) {
	    List<LOVDTO> list = new ArrayList<>();
	    for (int i = 0; i < jsonArray.length(); i++) {
	        JSONObject obj = jsonArray.getJSONObject(i);
	        LOVDTO model = new LOVDTO(obj.getString("label"), obj.getString("value")); // Assuming model structure
	        list.add(model);
	    }
	    return list;
	}
	@Override
	public String getAllDeptSubmit(DeptSubmitRequest deptrequest) {
		
		JSONObject response = new JSONObject();
	    try {
	        String userId = deptrequest.getUserId();
	        String companyCode = deptrequest.getCompanyCode();
	        String divisionCode = deptrequest.getDivisionCode();
	        String departmentCode = deptrequest.getDepartmentCode();

	        // Update existing record in LM_MENU_USER_COMP_DIVN table
	        int updatedRows = template.update("UPDATE lm_menu_user_comp_divn " +
	                                          "SET MUCD_DIVN_CODE = ?, MUCD_DEPT_CODE = ? " +
	                                          "WHERE MUCD_USER_ID = ? AND MUCD_COMP_CODE = ? AND MUCD_DIVN_CODE = ? AND MUCD_DEPT_CODE = ?",
	                                          divisionCode, departmentCode, userId, companyCode, divisionCode, departmentCode);

	        // If no existing record, insert new record
	        if (updatedRows == 0) {
	            template.update("INSERT INTO lm_menu_user_comp_divn (MUCD_USER_ID, MUCD_COMP_CODE, MUCD_DIVN_CODE, MUCD_DEPT_CODE) " +
	                            "VALUES (?, ?, ?, ?)", userId, companyCode, divisionCode, departmentCode);
	        }

	        // Update LM_MENU_USER_COMP table
	        template.update("UPDATE lm_menu_user_comp SET MUC_COMP_CODE = ? WHERE MUC_USER_ID = ?", companyCode, userId);

	        // If no existing record, insert new record
	        int rowCount = template.update("INSERT INTO lm_menu_user_comp (MUC_USER_ID, MUC_COMP_CODE) " +
	                                       "SELECT ?, ? FROM dual WHERE NOT EXISTS " +
	                                       "(SELECT 1 FROM lm_menu_user_comp WHERE MUC_USER_ID = ?)",
	                                       userId, companyCode, userId);

	        response.put("Status", "SUCCESS");
	        response.put("messageCode", "Data inserted/updated successfully");
	        
	        
	    } catch (Exception e) {
	    	 response.put("status", "ERROR");
	            response.put("message", "Error occurred: " + e.getMessage());
	    }
	    
	    return response.toString();
	}

	@Override
	public String getAllDeptDelete(DeptSubmitRequest user) {
	
	        JSONObject response = new JSONObject();
	        try {
	            String userId = user.getUserId();
	            String companyCode = user.getCompanyCode();
	            String divisionCode = user.getDivisionCode();
	            String departmentCode = user.getDepartmentCode();

	            // Delete record from LM_MENU_USER_COMP_DIVN table
	            int deletedRows = template.update("DELETE FROM lm_menu_user_comp_divn " +
	                                              "WHERE MUCD_USER_ID = ? AND MUCD_COMP_CODE = ? AND MUCD_DIVN_CODE = ? AND MUCD_DEPT_CODE = ?",
	                                              userId, companyCode, divisionCode, departmentCode);

	            if (deletedRows > 0) {
	                response.put("status", "SUCCESS");
	                response.put("message", "Data deleted successfully");
	            } else {
	                response.put("status", "ERROR");
	                response.put("message", "No record found to delete");
	            }
	        } catch (Exception e) {
	            response.put("status", "ERROR");
	            response.put("message", "Error occurred: " + e.getMessage());
	        }
	        
	        return response.toString();
	    }
	
	}



