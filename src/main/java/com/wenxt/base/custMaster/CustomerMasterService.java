package com.wenxt.base.custMaster;

import java.sql.SQLException;

import org.json.JSONException;

import com.wenxt.base.dto.CustomerRequestDto;

public interface CustomerMasterService {

	String getAllCustomlist() throws JSONException, SQLException;

	String getCustomerUserById(int custcode);

	String deleteUserById(int custcode);

//	String createCustomer(RequestDataDto requestData);

	String createCustomer(CustomerRequestDto requestData);


}