package com.wenxt.base.userMaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserMasterServiceImpl implements UserMasterService{
	
	@Autowired
	private UserMasterRepository userrepo;
	
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
		Optional<LM_MENU_USERS> optionalUser = userrepo.findById(lM_MENU_USER.getUser_id());

		if (optionalUser.isPresent()) {

			LM_MENU_USERS existingUser = optionalUser.get();

			userrepo.save(existingUser);
			return "User information updated successfully";
		} else {
			LM_MENU_USERS user = userrepo.save(lM_MENU_USER);
			response.put("Status", "SUCCESS");
			response.put("Message", createUser);
			data.put("Id", user.getUser_id());
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
	        userrepo.deleteById(userId);
	        response.put("Status","SUCCESS");
	        response.put("Message", deleteUserById);
	        return response.toString();
	    } catch (Exception e) {
	        return "Error deleting user with ID " + userId + ": " + e.getMessage();
	    }
	
}


	public String getUserById(String userId) {
		JSONObject response = new JSONObject();

		Optional<LM_MENU_USERS> user = userrepo.findById(userId);
		LM_MENU_USERS userResult = user.get();
			if (user.get() != null) {
				response.put("Status", "SUCCESS");
				response.put("Message", getUserById);
				response.put("Data", user);

				return response.toString();
			} else {
				response.put("Status", "FAILURE");
				return response.toString();
			}
	}



}




	

