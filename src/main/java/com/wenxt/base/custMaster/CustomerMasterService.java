package com.wenxt.base.custMaster;

import java.sql.SQLException;

import org.json.JSONException;

import com.wenxt.base.dto.CustomerRequestDto;
import com.wenxt.base.model.LM_CUST_CURR;
import com.wenxt.base.model.lm_cust_divn;

public interface CustomerMasterService {

	String getAllCustomlist() throws JSONException, SQLException;

	String getCustomerUserById(String custcode);

	String deleteUserById(String custcode);

	String createCustomer(CustomerRequestDto requestData);

	String addBranch(lm_cust_divn customerDivn);

	String updateBranch(lm_cust_divn customerDivn);

	String addCurrency(LM_CUST_CURR customerCurrency);

	String updateCurrency(LM_CUST_CURR customerCurrency);

}