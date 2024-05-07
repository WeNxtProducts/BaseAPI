package com.wenxt.base.custMaster;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import com.wenxt.base.commonUtils.LM_PROG_FIELD_DEFN_NEW;

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

	@Autowired
	private CommonDao commonDao;

	@Override
//	public String createCustomer(RequestDataDto requestData) {
//
//		LocalDateTime currentDate = LocalDateTime.now();
//		if (lmCustomer.getCustCode() == 0) {
//			// Customer is being created for the first time
//			lmCustomer.setCustInsDt(null);
//			cmrepo.save(lmCustomer);
//			return "LM customer created successfully";
//		} else {
//			// Customer already exists, update only modification date
//			LM_CUSTOMER existingCustomer = cmrepo.findById(lmCustomer.getCustCode()).orElse(null);
//			if (existingCustomer != null) {
//				// Set only modification date
//				existingCustomer.setCustModDt(currentDate);
//				cmrepo.save(existingCustomer);
//				return "LM customer updated successfully";
//			} else {
//				// Customer with given ID not found
//				return "Failed to update LM customer: Customer not found";
//			}
//		}
//	}

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
		try {
			Optional<LM_CUSTOMER> optionalEntity = cmrepo.findById(custcode);

			if (optionalEntity.isPresent()) {
				cmrepo.deleteById(custcode);

				JSONObject response = new JSONObject();
				response.put("Status", "SUCCESS");
				response.put("Message", "Record with ID " + custcode + " deleted successfully");
				return response.toString();

			} else {
				JSONObject response = new JSONObject();
				response.put("Status", "ERROR");
				response.put("Message", "Record with ID " + custcode + " not found");
				return response.toString();
			}
		} catch (Exception e) {
			JSONObject response = new JSONObject();
			response.put("Status", "ERROR");
			response.put("Message", "Error deleting record with ID " + custcode + ": " + e.getMessage());
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

//	@Override
//	public String createCustomer(RequestDataDto requestData) {
//		JSONObject response = new JSONObject();
//		JSONObject data = new JSONObject();
//
//		try {
//			Map<String, String> formFields = requestData.getFrontForm().getFormFields();
//			String custCodeStr = formFields.get("cust_code");
//		    
//			
//		    // Parse the String to Integer
//		    Integer custCode = Integer.parseInt(custCodeStr);
//		    
//		    Optional<LM_CUSTOMER> optionalUser = cmrepo.findByCustCode(custCode);
//			if (optionalUser.isPresent() == false) {
//				LM_CUSTOMER user = new LM_CUSTOMER();
//				for (Map.Entry<String, String> entry : formFields.entrySet()) {
//					String key = entry.getKey();
//					String value = entry.getValue();
//
//					Class classs = user.getClass();
//					Field field = null;
//					try {
//						field = classs.getDeclaredField(key);
//					} catch (Exception e) {
//
//					}
//					if (field != null) {
//						Class fieldType = field.getType();
//						Object convertedValue = convertStringToObject(value, fieldType);
//						String setterMethodName = "set" + key.substring(0, 1).toUpperCase()
//								+ key.substring(1, key.length());
//
//						if (value.isEmpty() == false && value != null) {
//							Method setter = classs.getMethod(setterMethodName, fieldType);
//							
//							setter.invoke(user, convertedValue);
//						}
//					}
//				}
//				try {
//
//					LM_CUSTOMER savedUser = cmrepo.save(user);
//					response.put(statusCode, successCode);
//					response.put(messageCode, "User created successfully");
//					data.put("Id", savedUser.getCustCode());
//					response.put(dataCode, data);
//				} catch (Exception e) {
//					e.printStackTrace();
//					response.put(statusCode, errorCode);
//					response.put(messageCode, "An error occurred: " + e.getMessage());
//				}
//			} else {
//
//				LM_CUSTOMER existingUser = optionalUser.get();
//
//				for (Map.Entry<String, String> entry : formFields.entrySet()) {
//					String key = entry.getKey();
//					String value = entry.getValue();
//
//					Class classs = existingUser.getClass();
//					Field field = null;
//
////					if (key.equals("user_email_id")) {
////						existingUser.setUserEmailId(value);
////					}
//					try {
//						field = classs.getDeclaredField(key);
//					} catch (Exception e) {
//
//					}
//					if (field != null) {
//						Class fieldType = field.getType();
//						Object convertedValue = convertStringToObject(value, fieldType);
//						String setterMethodName = "set" + key.substring(0, 1).toUpperCase()
//								+ key.substring(1, key.length());
//
//						if (value.isEmpty() == false && value != null) {
//							Method setter = classs.getMethod(setterMethodName, fieldType);
//
//							setter.invoke(existingUser, convertedValue);
//
//						}
//					}
//				}
//				try {
//					cmrepo.save(existingUser);
//					response.put(statusCode, successCode);
//					response.put(messageCode, "User information updated successfully");
//					data.put("Id", existingUser.getCustCode());
//					response.put(dataCode, data);
//					System.out.println(data);
//				} catch (Exception e) {
//
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return response.toString();
//	}
//	
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
//CUSTOMERMASTER STATIC API START
//	@Override
//	public String createCustomer(CustRequestData requestData) {
//		JSONObject response = new JSONObject();
//		JSONObject data = new JSONObject();
//
//		try {
//			Map<String, String> formFields = requestData.getFrontForm().getFormFields();
//			Map<String, String> address1 = requestData.getPhysical_address().getFormFields();
//			Map<String, String> address2 = requestData.getPersonal_details().getFormFields();
//			Map<String, String> address3 = requestData.getPostal_address().getFormFields();
//
//			String custCodeStr = formFields.get("CUST_CODE");
//
//			// Parse the String to Integer
//			Integer custCode = Integer.parseInt(custCodeStr);
//
//			Optional<LM_CUSTOMER> optionalUser = cmrepo.findByCustCode(custCode);
//			LM_CUSTOMER user;
//
//			if (!optionalUser.isPresent()) {
//				user = new LM_CUSTOMER();
//			} else {
//				user = optionalUser.get();
//			}
//
//			// Set formFields
//			for (Map.Entry<String, String> entry : formFields.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Set address1 fields
//			for (Map.Entry<String, String> entry : address1.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Set address2 fields
//			for (Map.Entry<String, String> entry : address2.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Set address3 fields
//			for (Map.Entry<String, String> entry : address3.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Save or update user to database
//			try {
//				LM_CUSTOMER savedUser = cmrepo.save(user);
//				response.put(statusCode, successCode);
//				response.put(messageCode, "User created successfully");
//				data.put("Id", savedUser.getCustCode());
//				response.put("data", data);
//			} catch (Exception e) {
//				response.put("statusCode", 500);
//				response.put("message", "An error occurred: " + e.getMessage());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.put("statusCode", 500);
//			response.put("message", "An error occurred: " + e.getMessage());
//		}
//
//		return response.toString();
//	}

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
	// CUSTOMERMASTER STATIC API END

//	@Override
//	public String createCustomer(CustRequestData requestData) {
//		JSONObject response = new JSONObject();
//		JSONObject data = new JSONObject();
//
//		try {
//			// Fetch fields from the database
//			List<LM_PROG_FIELD_DEFN_NEW> fields = commonDao.getAccordianHeaderTabs("CUSTOMERMASTER", "CUSTCREATE");
//
//			// Construct request body dynamically
//			JSONObject requestBody = new JSONObject();
//			for (LM_PROG_FIELD_DEFN_NEW field : fields) {
//				if (field != null && field.getPFD_FLD_NAME() != null && !field.getPFD_FLD_NAME().isEmpty()) {
//					String formItemType = field.getPFD_FLD_NAME();
//					JSONObject formItem = new JSONObject();
//					formItem.put("Label", formItemType);
//					JSONObject formFields = new JSONObject();
//					formItem.put("formFields", formFields); // Initialize formFields
//					requestBody.put(formItemType, formItem);
//				}
//			}
//
////			for (LM_PROG_FIELD_DEFN_NEW field : fields) {
////				if (field != null
////
////						&& field.getPFD_FLD_NAME() != null && !field.getPFD_FLD_NAME().isEmpty()) {
////
////					String fieldName = field.getPFD_FLD_NAME();
////					String formItemType = field.getPFD_FORM_ITEM_TYPE2();
////
////					JSONObject formFields = requestBody.getJSONObject(fieldName).getJSONObject("formFields");
////
//////					String column = field.getPFD_COLUMN_NAME();
////					System.out.println("KK");
//////					formFields.put(column, "");
////					
////					String column = field.getPFD_COLUMN_NAME();
////					if (column != null && !column.isEmpty()) {
////					    // Your code here
////					    formFields.put(column, ""); // Or put whatever value you need
////					}
////				}
//
////			}
//
//			for (LM_PROG_FIELD_DEFN_NEW field : fields) {
//				if (field != null && field.getPFD_FLD_NAME() != null && !field.getPFD_FLD_NAME().isEmpty()) {
//					String formItemType = field.getPFD_FLD_NAME();
//
//					JSONObject formItem = new JSONObject();
//
//					formItem.put("Label", formItemType);
//					
//					
//
//					// Create the formFields object with getPFD_COLUMN_NAME
//					JSONObject formFields = new JSONObject();
//					formFields.put("getPFD_COLUMN_NAME", "");
//					formItem.put("formFields", formFields);
//
//					requestBody.put(formItemType, formItem);
//				}
//			}
//
//			// Perform the Postman request here
//
//			// Dummy response for demonstration
//			response.put("statusCode", 200);
//			response.put("message", "Postman requests sent successfully");
//
//			// Extracting formFields from requestBody
//			response.put("data", requestBody);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.put("statusCode", 500);
//			response.put("message", "An error occurred: " + e.getMessage());
//		}
//
//		return response.toString();
////	}
//***********
//	07-05-2024

//	public String createCustomer(CustRequestData requestData) {
//		JSONObject response = new JSONObject();
//		JSONObject data = new JSONObject();
//
//		try {
//			Map<String, String> formFields = requestData.getFrontForm().getFormFields();
//			Map<String, String> address1 = requestData.getPhysical_address().getFormFields();
//			Map<String, String> address2 = requestData.getPersonal_details().getFormFields();
//			Map<String, String> address3 = requestData.getPostal_address().getFormFields();
//
//			String custCodeStr = formFields.get("CUST_CODE");
//
//			// Parse the String to Integer
//			Integer custCode = Integer.parseInt(custCodeStr);
//
//			Optional<LM_CUSTOMER> optionalUser = cmrepo.findByCustCode(custCode);
//			LM_CUSTOMER user;
//
//			if (!optionalUser.isPresent()) {
//				// User does not exist, create a new user
//				user = new LM_CUSTOMER();
//			} else {
//				// User exists, update the existing user
//				user = optionalUser.get();
//			}
//
//			// Set formFields
//			for (Map.Entry<String, String> entry : formFields.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Set address1 fields
//			for (Map.Entry<String, String> entry : address1.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Set address2 fields
//			for (Map.Entry<String, String> entry : address2.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Set address3 fields
//			for (Map.Entry<String, String> entry : address3.entrySet()) {
//				setUserField(user, entry.getKey(), entry.getValue());
//			}
//
//			// Save or update user to database
//			try {
//				LM_CUSTOMER savedUser = cmrepo.save(user);
//				if (!optionalUser.isPresent()) {
//					response.put(statusCode, successCode);
//					response.put(messageCode, "User created successfully");
//				} else {
//					response.put(statusCode, successCode);
//					response.put(messageCode, "User updated successfully");
//				}
//				data.put("Id", savedUser.getCustCode());
//				response.put("data", data);
//			} catch (Exception e) {
//				response.put("statusCode", 500);
//				response.put("message", "An error occurred: " + e.getMessage());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.put("statusCode", 500);
//			response.put("message", "An error occurred: " + e.getMessage());
//		}
//
//		return response.toString();
//	}
	public String createCustomer(CustRequestData requestData) {
	    JSONObject response = new JSONObject();
	    JSONObject data = new JSONObject();

	    try {
	        Map<String, String> formFields = requestData.getFrontForm().getFormFields();
	        mergeMaps(formFields, requestData.getPhysical_address().getFormFields(), "physical_");
	        mergeMaps(formFields, requestData.getPersonal_details().getFormFields(), "personal_");
	        mergeMaps(formFields, requestData.getPostal_address().getFormFields(), "postal_");

	        String custCodeStr = formFields.get("CUST_CODE");

	        // Parse the String to Integer
	        Integer custCode = Integer.parseInt(custCodeStr);

	        Optional<LM_CUSTOMER> optionalUser = cmrepo.findByCustCode(custCode);
	        LM_CUSTOMER user;

	        if (!optionalUser.isPresent()) {
	            // User does not exist, create a new user
	            user = new LM_CUSTOMER();
	        } else {
	            // User exists, update the existing user
	            user = optionalUser.get();
	        }

	        // Set formFields
	        for (Map.Entry<String, String> entry : formFields.entrySet()) {
	            setUserField(user, entry.getKey(), entry.getValue());
	        }

	        // Save or update user to database
	        try {
	            LM_CUSTOMER savedUser = cmrepo.save(user);
	            if (!optionalUser.isPresent()) {
	                response.put(statusCode, successCode);
	                response.put(messageCode, "User created successfully");
	            } else {
	                response.put(statusCode, successCode);
	                response.put(messageCode, "User updated successfully");
	            }
	            data.put("Id", savedUser.getCustCode());
	            response.put("data", data);
	        } catch (Exception e) {
	            response.put(statusCode, errorCode);
	            response.put(messageCode, "An error occurred: " + e.getMessage());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put(statusCode, statusCode);
	        response.put(messageCode, "An error occurred: " + e.getMessage());
	    }

	    return response.toString();
	}

	private void mergeMaps(Map<String, String> mainMap, Map<String, String> mapToMerge, String prefix) {
	    for (Map.Entry<String, String> entry : mapToMerge.entrySet()) {
	        mainMap.put(prefix + entry.getKey(), entry.getValue());
	    }
	}


//	@Override
//	public String createerequest(CustRequestData requestData) {
//	    JSONObject response = new JSONObject();
//
//	    try {
//	        // Fetch fields from the database
//	        List<LM_PROG_FIELD_DEFN_NEW> fields = commonDao.getAccordianHeaderTabs("CUSTOMERMASTER", "CUSTCREATE");
//
//	        // Construct request body dynamically
//	        JSONObject requestBody = new JSONObject();
//	        for (LM_PROG_FIELD_DEFN_NEW field : fields) {
//	            if (field != null && field.getPFD_FLD_NAME() != null && !field.getPFD_FLD_NAME().isEmpty()) {
//	                String formItemType = field.getPFD_FLD_NAME();
//	                JSONObject formItem = new JSONObject();
//
//	                // Fetch column names dynamically
//	                List<LM_PROG_FIELD_DEFN_NEW> columns = commonDao.getcolumn("CUSTOMERMASTER", "CUSTCREATE");
//
//	                if (columns != null && !columns.isEmpty()) {
//	                    JSONObject formFields = new JSONObject();
//	                    for (LM_PROG_FIELD_DEFN_NEW column : columns) {
//	                        String columnName = column.getPFD_COLUMN_NAME();
//	                        if (columnName != null && !columnName.isEmpty()) {
//	                            // Check if PFD_FLD_NAME matches any of the keys in the formFields map
//	                            if (requestData.getFrontForm().getFormFields().containsKey(columnName)) {
//	                                formFields.put(columnName, "");
//	                                System.out.println("12");
//	                            } else {
//	                                formFields.put(formItemType, "");
//	                                System.out.println("23");
//	                            }
//	                        }
//	                    }
//
//	                    formItem.put("Label", formItemType);
//	                    formItem.put("formFields", formFields);
//	                    requestBody.put(formItemType, formItem);
//	                }
//	            }
//	        }
//
//	        // Perform the Postman request here
//
//	        // Dummy response for demonstration
//	        response.put("statusCode", 200);
//	        response.put("message", "Postman requests sent successfully");
//	        response.put("data", requestBody);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        response.put("statusCode", 500);
//	        response.put("message", "An error occurred: " + e.getMessage());
//	    }
//
//	    return response.toString();
//	}

}
