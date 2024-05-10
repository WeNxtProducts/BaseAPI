package com.wenxt.base.custMaster;
import java.sql.SQLException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wenxt.base.dto.CustomerRequestDto;

@RestController
@RequestMapping("/customer")
public class CustomerMasterController {

	@Autowired
	private CustomerMasterService custom;

	@PostMapping("/create")
	public String createCustomer(@RequestBody CustomerRequestDto requestData) {
		return custom.createCustomer(requestData);
	}

	@GetMapping("/customerList")
	public String getAllCustomlist() throws JSONException, SQLException {
		return custom.getAllCustomlist();
	}

	@GetMapping("getcustomer/{custCode}")
	public String getCustomerUserById(@PathVariable String custCode) {
		return custom.getCustomerUserById(custCode);

	}

	@DeleteMapping("/deletes/{custCode}")
	public String deleteUser(@PathVariable String custCode) {
		return custom.deleteUserById(custCode);
	}
	
	


}