package com.wenxt.base.custMaster;

import java.sql.SQLException;

import org.json.JSONException;

import com.wenxt.base.dto.CustomerRequestDto;

public interface CustomerMasterService {

	String getAllCustomlist() throws JSONException, SQLException;

	String getCustomerUserById(String custcode);

	String deleteUserById(String custcode);

	String createCustomer(CustomerRequestDto requestData);

}