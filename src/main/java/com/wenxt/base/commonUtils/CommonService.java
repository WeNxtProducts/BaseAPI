package com.wenxt.base.commonUtils;

import org.springframework.scheduling.annotation.Async;

import jakarta.servlet.http.HttpServletRequest;

public interface CommonService {
	
	public String insertAudit(APP_AUDIT auditModel);
	
	public String getAuditByUserAndScreen(APP_AUDIT auditModel);
	
	public String insertException(ExceptionDTO exceptionDTO);
	
	public String getAllExceptions();
	
	public String getMenuList(String groupId, HttpServletRequest request);
	
	public String getQueryLOV(HttpServletRequest request);
	
	public String getQueryParamLOV(HttpServletRequest request);
	
	public String getListingData(HttpServletRequest request);
	
	public String getMrvListing(HttpServletRequest request);
	
	public String getFieldList(HttpServletRequest request);
	
	public void asyncMethod(AsyncDTO inputRequest, HttpServletRequest request);
	
	public void syncMethod(AsyncDTO inputRequest, HttpServletRequest request);
	
	public void saveFieldDefJson(HttpServletRequest request);
	
	public void serviceToJson(HttpServletRequest request);

}
