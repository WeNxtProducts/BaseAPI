package com.wenxt.base.custMaster;

import java.sql.SQLException;

import org.json.JSONException;

import com.wenxt.base.commonUtils.LM_CUSTOMER;
import com.wenxt.base.userMaster.RequestDataDto;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerMasterService {

	String getAllCustomlist() throws JSONException, SQLException;

	String getCustomerUserById(int custcode);

	String deleteUserById(int custcode);

//	String createCustomer(RequestDataDto requestData);

	String createCustomer(CustRequestData requestData);


}