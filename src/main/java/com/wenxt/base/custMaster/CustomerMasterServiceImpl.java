package com.wenxt.base.custMaster;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wenxt.base.commonUtils.CommonDao;
import com.wenxt.base.commonUtils.LM_CUSTOMER;
import com.wenxt.base.dto.CustomerRequestDto;

@Service
public class CustomerMasterServiceImpl implements CustomerMasterService {

	@Autowired
	private CustomerRepository cmrepo;

//	@Value("${insert.message.logs}")
//	private String getallLists;

	private static final String JDBC_URL = "jdbc:mysql://baseapi.cr4u8emg2x3o.eu-north-1.rds.amazonaws.com:3306/baseapi";
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "D3Vt3aM#92";

	@Value("${spring.message.code}")
	private String messageCode;

	@Value("${spring.status.code}")
	private String statusCode;

	@Value("${spring.data.code}")
	private String dataCode;

	@Value("${spring.success.code}")
	private String successCode;

	@Value("${spring.error.code}")
	private String errorCode;

	@Value("${spring.warning.code}")
	private String warningCode;

	@Override
	public String getAllCustomlist() throws JSONException, SQLException {

		String query = "SELECT cust_code,cust_class,cust_type,cust_name,cust_short_name,cust_dob,cust_catg_code,cust_addr1,cust_addr2,cust_addr3,cust_contact,cust_phone,cust_city,cust_country,cust_mar_status,cust_salutation,cust_ml_name,cust_ml_short_name,cust_ml_addr1,cust_ml_addr2,cust_ml_addr3,cust_ml_contact,cust_ml_phone,cust_ml_city,cust_ml_country,cust_frz_flag,cust_ins_dt,cust_ins_id,cust_mod_dt,cust_mod_id,cust_national_id,cust_credit_days,cust_occupation,cust_mobile_no,cust_qualfn,cust_eff_fm_dt,cust_eff_to_dt,cust_agency_name,cust_agent_rank_code,cust_lcn_no,cust_lic_start_dt,cust_lic_end_dt,cust_lcn_reg_dt,cust_lcn_status,cust_spouse_name,cust_status,cust_flex_01,cust_flex_02,cust_flex_03,cust_flex_04,cust_flex_05,cust_flex_06,cust_flex_07,cust_flex_08,cust_flex_09,cust_flex_10,cust_employer_code,cust_comp_code FROM lm_customer";

		ResultSet headerResultSet = executeQuery(query);

		// Creating the header JSONObject
		JSONObject header = new JSONObject();
		ResultSetMetaData rsmd = headerResultSet.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			header.put(columnName, columnName);
		}

		List<LM_CUSTOMER> exceptions = cmrepo.findAll();

		JSONObject response = new JSONObject();
		response.put("Status", "SUCCESS");
		response.put("Message", "listed sucees");
		response.put("Heading", header);
		response.put("Data", exceptions);

		return response.toString();

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

	public String deleteUserById(int custcode) {

		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();
		Optional<LM_CUSTOMER> optionalUser = cmrepo.findById(custcode);
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(currentDate);

		if (optionalUser.isPresent()) {
			LM_CUSTOMER existingUser = optionalUser.get();

			existingUser.setCust_eff_fm_dt(formattedDate);
			existingUser.setCust_eff_to_dt(formattedDate);
			existingUser.setCust_frz_flag("Y");

			cmrepo.save(existingUser);

			response.put(statusCode, successCode);
			response.put(messageCode, "CUST CODE" + custcode + " has been deleted");
			data.put("Id", existingUser.getCustCode());
			response.put(dataCode, data);

			return response.toString();
		} else {
			response.put(statusCode, errorCode);
			response.put(messageCode, "Cust code not found with ID: " + custcode);
			return response.toString();
		}

	}

	public String getCustomerUserById(int custcode) {
		LM_CUSTOMER lmCustClass = cmrepo.findById(custcode)
				.orElseThrow(() -> new RuntimeException("LM CLASS not found"));

		JSONObject response = new JSONObject(lmCustClass);

		response.put("Status", "SUCCESS");
		response.put("Message", "Record with ID " + custcode + " retrived successfully");
		return response.toString();

	}

	public String createCustomer(CustomerRequestDto requestData) {
		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();

		try {
			Integer custCode = Integer.parseInt(requestData.getFrontForm().getFormFields().get("custCode"));
			Optional<LM_CUSTOMER> optionalUser = cmrepo.findByCustCode(custCode);
			LM_CUSTOMER user = optionalUser.orElse(new LM_CUSTOMER());

			Map<String, Map<String, String>> fieldMaps = new HashMap<>();
			fieldMaps.put("frontForm", requestData.getFrontForm().getFormFields());
			fieldMaps.put("physicalAddress", requestData.getPhysical_address().getFormFields());
			fieldMaps.put("personalDetails", requestData.getPersonal_details().getFormFields());
			fieldMaps.put("postalAddress", requestData.getPostal_address().getFormFields());
			fieldMaps.put("bankDetails", requestData.getBank_details().getFormFields());
			
//			fieldMaps.put("address", requestData.getAddress().getFormFields());
//			fieldMaps.put("relationHistory", requestData.getRelation_History().getFormFields());
//			fieldMaps.put("customer", requestData.getCustomer().getFormFields());
//			fieldMaps.put("moreInfo", requestData.getMore_info().getFormFields());
//			fieldMaps.put("contactDetails", requestData.getContact_details().getFormFields());
//			fieldMaps.put("taxInfo", requestData.getTax_info().getFormFields());
//			fieldMaps.put("staticDetails",requestData.getStaticDetails().getFormFields());
//			fieldMaps.put("branch", requestData.getBranch().getFormFields());
//			fieldMaps.put("currency", requestData.getCurrency().getFormFields());
//			fieldMaps.put("corporateDetails", requestData.getCorporate_details().getFormFields());
//			fieldMaps.put("employerInformation",requestData.getEmployer_information().getFormFields());
			// Set fields
			for (Map.Entry<String, Map<String, String>> entry : fieldMaps.entrySet()) {
				setUserFields(user, entry.getValue());
			}

			// Save or update user to database
			try {
				LM_CUSTOMER savedUser = cmrepo.save(user);
				response.put(statusCode, 200);
				response.put(messageCode,
						optionalUser.isPresent() ? "User updated successfully" : "User created successfully");
				data.put("Id", savedUser.getCustCode());
				response.put("data", data);
			} catch (Exception e) {
				response.put("statusCode", 500);
				response.put("message", "An error occurred: " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("statusCode", 500);
			response.put("message", "An error occurred: " + e.getMessage());
		}

		return response.toString();
	}

	private void setUserFields(LM_CUSTOMER user, Map<String, String> fields) throws Exception {
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			setUserField(user, entry.getKey(), entry.getValue());
		}
	}

	private Object dateTimeConverter(String value) {
		String dateString = value;
		if (value.length() > 10) {
			dateString = value.substring(0, 10);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalTime defaultTime = LocalTime.of(0, 0, 0);
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		LocalDateTime dateTime = LocalDateTime.of(localDate, defaultTime);
		String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatters);
		return parsedDateTime;
	}

	private Object convertStringToObject(String value, Class<?> fieldType) {
		if (fieldType.equals(Integer.class) && value.isEmpty() == false && value != null) {
			return Integer.parseInt(value);
		} else if (fieldType.equals(Double.class) && value.isEmpty() == false && value != null) {
			return Double.parseDouble(value);
		} else if (fieldType.equals(Short.class) && value.isEmpty() == false && value != null) {
			return Short.parseShort(value);
		} else if (fieldType.equals(LocalDateTime.class) && value.isEmpty() == false && value != null) {
			return dateTimeConverter(value);
		} else {
			return value;
		}
	}

	private void setUserField(LM_CUSTOMER user, String fieldName, String value) throws Exception {
		try {
			Field field = LM_CUSTOMER.class.getDeclaredField(fieldName);
			Class<?> fieldType = field.getType();
			Object convertedValue = convertStringToObject(value, fieldType);
			String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

			if (value != null && !value.isEmpty()) {
				Method setter = LM_CUSTOMER.class.getMethod(setterMethodName, fieldType);
				setter.invoke(user, convertedValue);
			}
		} catch (NoSuchFieldException e) {
			// Field not found, continue to the next field
		}
	}

}
