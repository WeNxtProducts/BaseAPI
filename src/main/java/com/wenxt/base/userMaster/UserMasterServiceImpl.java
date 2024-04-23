package com.wenxt.base.userMaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterRepository userrepo;

	@Autowired
	private StringEncryptor encryptor;

	@Value("${get.user.list}")
	private String getallList;

	@Value("${get.user.byId}")
	private String getUserById;

	@Value("${delete.user.byId}")
	private String deleteUserById;

	@Value("${create.new.user}")
	private String createUser;

	private static final String JDBC_URL = "jdbc:mysql://baseapi.cr4u8emg2x3o.eu-north-1.rds.amazonaws.com:3306/baseapi";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "D3Vt3aM#92";

	public String createUser(LM_MENU_USERS lM_MENU_USER) {
		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();
		Optional<LM_MENU_USERS> optionalUser = userrepo.findById(lM_MENU_USER.getUserId());

		if (optionalUser.isPresent()) {

			LM_MENU_USERS existingUser = optionalUser.get();

			userrepo.save(existingUser);
			return "User information updated successfully";
		} else {
			lM_MENU_USER.setUser_passwd(encryptor.encrypt(lM_MENU_USER.getUser_passwd()));
			LM_MENU_USERS user = userrepo.save(lM_MENU_USER);

			response.put("Status", "SUCCESS");
			response.put("Message", createUser);
			data.put("Id", user.getUserId());
			response.put("Data", data);
			return response.toString();
		}
	}

	public String getAllUserlist() {
		String query = "SELECT user_passwd, user_id, user_desc, user_group_id, user_bl_desc, user_start_menu_id, user_ask_passwd_flag, user_change_passwd_flag, user_disable_flag, user_default_printer, user_rep_userid_passwd, user_tel_no, user_tel_extn, user_fax_no, user_login_count, user_ext_yn, user_vps, user_copies, user_prn_code, user_pf_code, user_override_yn, user_ins_Dt, user_ins_id, user_mod_Dt, user_mod_id, user_comp_code, user_administrator_yn, user_email_id, user_mobile_no, user_bl_disp_yn_num, user_vps_flag, user_res_tel_no, user_remarks, user_expiry_day_count, user_passwd_expiry_yn, user_expiry_dt, user_warning_days, user_first_login_yn, user_frz_flag, user_initial, user_designation, user_desig_desc, user_logon_option, user_status, user_eff_to_dt, user_ter_reason, user_credit_card_upd_yn, user_valid_upto, user_pass_exp_date, user_fstlog_flag, user_locked_yn, user_no_of_failed_logins, user_lock_reason, user_email, user_port_code, user_client_id, user_client_code, user_dft_company_code, user_pwd_remind_num, user_pwd_wrong_allow_num, user_pwd_wrong_limit_yn, user_security_code, user_inst_code, user_ds_group_id, user_pwd_reuse_count, user_dflt_lang_code, user_signature FROM lm_menu_users";
		JSONObject response = new JSONObject();

		ResultSet headerResultSet;
		try {
			headerResultSet = executeQuery(query);

			// Creating the header JSONObject
			JSONObject header = new JSONObject();
			if (headerResultSet.next()) {
				header.put("user_desc", "Demo user");
				header.put("user_id", "ID");
				header.put("user_passwd", "PASSWORD");
				header.put("user_pwd_remind_num", "PNUM");
				header.put("user_ins_id", "USERID");
				header.put("user_ins_Dt", "INS_DATE");
				header.put("user_pwd_wrong_limit_yn", "YEAR");
				header.put("user_group_id", "GROUP_ID");

			}

			List<LM_MENU_USERS> exceptions = userrepo.findAll();

			response.put("Status", "SUCCESS");
			response.put("Message", getallList);
			response.put("Heading", header);
			response.put("Data", exceptions);

			return response.toString();
		} catch (SQLException e) {
			response.put("Status", "FAILURE");
			response.put("Message", e.getMessage());
			e.printStackTrace();

			return response.toString();
		}
	}

	private ResultSet executeQuery(String query) throws SQLException {
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Establish a connection
		Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

		// Create a statement
		Statement statement = connection.createStatement();

		// Execute the query and return the result set
		return statement.executeQuery(query);
	}

	public String deleteUserById(String userId) {
		JSONObject response = new JSONObject();
		try {
			Optional<LM_MENU_USERS> optionalEntity = userrepo.findById(userId);

			if (optionalEntity.isPresent()) {
				userrepo.deleteById(userId);

				response.put("Status", "SUCCESS");
				response.put("Message", "User with ID " + userId + " deleted successfully");
				return response.toString();

			} else {
				response.put("Status", "ERROR");
				response.put("Message", "User with ID " + userId + " not found");
				return response.toString();
			}
		} catch (Exception e) {
			response.put("Status", "ERROR");
			response.put("Message", "Error deleting user with ID " + userId + ": " + e.getMessage());
			return response.toString();
		}
	}

	public String getUserById(String userId) {
		LM_MENU_USERS lmCustClass = userrepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("LM CLASS not found"));

		JSONObject response = new JSONObject(lmCustClass);

		response.put("Status", "SUCCESS");
		response.put("Message", "Record with ID " + userId + " retrived successfully");
		return response.toString();

	}

	@Override

	public String createLmUser(RequestDataDto requestData) {
		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			Map<String, String> formFields = requestData.getFrontForm().getFormFields();
			String userInitial = formFields.get("user_initial");
			String userRemarks = formFields.get("user_remarks");
			String userId = formFields.get("user_id");
			double userWarningDays = Double.parseDouble(formFields.get("user_warning_days"));
			String userLogonOption = formFields.get("user_logon_option");
			short userLoginCount = Short.parseShort(formFields.get("user_login_count"));
			Integer userExpiryDayCount = Integer.parseInt(formFields.get("user_expiry_day_count"));
			String userLockedYN = formFields.get("user_locked_yn");
			double userPwdReuseCount = Double.parseDouble(formFields.get("user_pwd_reuse_count"));
//          LocalDateTime userExpiryDt = LocalDateTime.parse(formFields.get("user_expiry_dt"), formatter);
			String userEmailId = formFields.get("user_email_id");
			String userMobileNo = formFields.get("user_mobile_no");
			String userTelNo = formFields.get("user_tel_no");
			String userPasswd = formFields.get("user_passwd");
			String userPasswdExpiryYN = formFields.get("user_passwd_expiry_yn");
			String userAdministratorYN = formFields.get("user_administrator_yn");
			String userOverrideYN = formFields.get("user_override_yn");
			short userCopies = Short.parseShort(formFields.get("user_copies"));
//          short userCopies = Short.parseShort(formFields.get("user_copies"));
			String userDsGroupId = formFields.get("user_ds_group_id");

			LM_MENU_USERS newUser = new LM_MENU_USERS();
			newUser.setUser_initial(userInitial);
			newUser.setUser_remarks(userRemarks);
			newUser.setUserId(userId);
			newUser.setUser_warning_days(userWarningDays);
			newUser.setUser_logon_option(userLogonOption);
			newUser.setUser_login_count(userLoginCount);
			newUser.setUser_expiry_day_count(userExpiryDayCount);
			newUser.setUser_locked_yn(userLockedYN);
			newUser.setUser_pwd_reuse_count(userPwdReuseCount);
//          newUser.setUser_expiry_dt(userExpiryDt);
			newUser.setUserEmailId(userEmailId);
			newUser.setUser_mobile_no(userMobileNo);
			newUser.setUser_tel_no(userTelNo);

			newUser.setUser_passwd(encryptor.encrypt(userPasswd));

			newUser.setUser_passwd_expiry_yn(userPasswdExpiryYN);
			newUser.setUser_administrator_yn(userAdministratorYN);
			newUser.setUser_override_yn(userOverrideYN);
			newUser.setUser_copies(userCopies);
			newUser.setUser_ds_group_id(userDsGroupId);

			LocalDateTime currentDate = LocalDateTime.now();
//			newUser.setUser_ins_Dt(currentDate);

			newUser.setUser_ds_group_id(userDsGroupId);
//			Optional<LM_MENU_USERS> optionalUser = userrepo.findById(newUser.getUserId());
//			if (optionalUser.isPresent()) {
//				LM_MENU_USERS existingUser = optionalUser.get();
//
//				newUser.setUser_mod_Dt(currentDate);
//				userrepo.save(newUser);
//				response.put("Status", "SUCCESS");
//				response.put("Message", "User information updated successfully");
//				data.put("Id", existingUser.getUserId());
//			} else {
//				
//				
//				newUser.setUser_ins_Dt(currentDate);
//				LM_MENU_USERS savedUser = userrepo.save(newUser);
//
//				response.put("Status", "SUCCESS");
//				response.put("Message", "User created successfully");
//				data.put("Id", savedUser.getUserId());
//			}

			Optional<LM_MENU_USERS> optionalUser = userrepo.findById(newUser.getUserId());

			if (optionalUser.isPresent()) {
			    LM_MENU_USERS existingUser = optionalUser.get();

			    // Keep the existing user_ins_Dt value
			    if (existingUser.getUser_ins_Dt() != null) {
			        newUser.setUser_ins_Dt(existingUser.getUser_ins_Dt());
			    }
			    
			    newUser.setUser_ins_id(existingUser.getUserId());
			    newUser.setUser_mod_Dt(currentDate);
			    userrepo.save(newUser);
			    response.put("Status", "SUCCESS");
			    response.put("Message", "User information updated successfully");
			    data.put("Id", existingUser.getUserId());
			} else {
//				 LM_MENU_USERS User = optionalUser.get();
			    // Set user_ins_Dt only when user is created
			    newUser.setUser_ins_Dt(currentDate);
			    newUser.setUser_mod_id(newUser.getUserId());
			    LM_MENU_USERS savedUser = userrepo.save(newUser);

			    response.put("Status", "SUCCESS");
			    response.put("Message", "User created successfully");
			    data.put("Id", savedUser.getUserId());
			}

			response.put("Data", data);
		} catch (Exception e) {
			response.put("Status", "FAILURE");
			response.put("Message", "An error occurred: " + e.getMessage());
		}

		return response.toString();
	}

}
