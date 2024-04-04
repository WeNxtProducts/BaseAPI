package com.wenxt.base.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequestModel login) {
		return loginService.login(login);
	}
	
	@PostMapping("/getCompList")
	public String getCompany(@RequestBody LoginDropDownRequestModel user) {
		return loginService.getCompany(user);
	}
	
	@PostMapping("/getBranchList")
	public String getBranch(@RequestBody LoginDropDownRequestModel user) {
		return loginService.getBranch(user);
	}
	
	@PostMapping("/getDept")
	public String getDept(@RequestBody LoginDropDownRequestModel user) {
		return loginService.getDept(user);
	}
	
	@PostMapping("/getLang")
	public String getLang() {
		return loginService.getLang();
	}
	
	@PostMapping("/reset")
    public String resetPassword(@RequestParam String name,
                                @RequestParam String password,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword) {
        return loginService.resetPassword(name, password, newPassword, confirmPassword);
    }

	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email) throws Exception {
			return loginService.processForgotPassword(email);
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
		try {
			loginService.resetPassword(token, newPassword);
			return "Password reset successful";
		} catch (InvalidTokenException e) {
			return "Invalid token";
		}
	}

}
