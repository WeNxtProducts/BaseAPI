package com.wenxt.base.commonUtils;

import java.util.List;
import java.util.Map;

public interface CommonDao {

	List<MenuResultDTO> getMenuList(String groupId, String query);
	
	List<MenuResultDTO> getChildMenuList(String parentId, String groupId, String query);
	
	QUERY_MASTER getQueryLov(int queryId);
	
	List<QUERY_PARAM_MASTER> getQueryParams(int sysId);
	
	List<LOVDTO> executeLOVQuery(String query, Map<String, Object> paramList);
	
	List<Map<String, Object>> getListingData(String query);
	
	List<LM_PROG_FIELD_DEFN_NEW> getFieldList(String screenCode, String screenName, String query);
	
	service_url_mapping getUrlData(AsyncDTO object);

	List<Map<String, Object>> getMrvListing(String query, Map<String, Object> paramsList);

}
