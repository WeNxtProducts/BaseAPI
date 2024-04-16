package com.wenxt.base.commonUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private CommonService service;
	
	@PostMapping("/insertAudit")
	public String insertAudit(@RequestBody APP_AUDIT auditModel) {
		return service.insertAudit(auditModel);
	}
	
	@PostMapping("/getAuditByUserAndScreen")
	public String getAuditByUserAndScreen(@RequestBody APP_AUDIT auditModel) {
		return service.getAuditByUserAndScreen(auditModel);
	}
 
    @PostMapping("/insertException")
    public ResponseEntity<String> insertException(@RequestBody ExceptionDTO exceptionDTO) {
        String result = service.insertException(exceptionDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/getExceptionList")
    public String getAllExceptions() {
        return service.getAllExceptions();
    }
	
	@GetMapping("/getMenuList")
	public String getMenuList(@RequestParam String groupId, HttpServletRequest request) {
		return service.getMenuList(groupId, request);
	}
	
	@GetMapping("/getlov")
	public String getQueryLov(HttpServletRequest request) {
		return service.getQueryLOV(request);
	}
	
	@GetMapping("/getparamlov")
	public String getQueryParamLov(@Context UriInfo uriInfo ,HttpServletRequest request) {
		return service.getQueryParamLOV(request);
	}
	
	@GetMapping("/getlistingdata")
	public String getListingData(HttpServletRequest request) {
		return service.getListingData(request);
	}
	
	@PostMapping("/getMrvListing")
	public String getMrvListing(HttpServletRequest request) {
		return service.getMrvListing(request);
	}
	
	@GetMapping("/getfield")
	public String getFieldList(HttpServletRequest request) {
		return service.getFieldList(request);
	}
	
	@PostMapping("/runasyncservice")
	public void runAsyncService(@RequestBody AsyncDTO object, HttpServletRequest request) {
		service.asyncMethod(object, request);
	}
	
	@PostMapping("/runsyncservice")
	public void runSyncService(@RequestBody AsyncDTO inputRequest, HttpServletRequest request) {
		service.syncMethod(inputRequest, request);
	}
	
	@PostMapping("/saveFieldDefJson")
	public void saveFieldDefJson(HttpServletRequest request) {
		service.saveFieldDefJson(request);
	}
	
	@PostMapping("/serviceToJson")
	public void serviceToJson(HttpServletRequest request) {
		service.serviceToJson(request);
	}
	
	@PostMapping("/test")
	public String test(HttpServletRequest request) {
		return service.test(request);
	}

}
