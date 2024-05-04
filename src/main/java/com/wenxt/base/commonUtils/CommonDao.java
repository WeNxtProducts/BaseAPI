package com.wenxt.base.commonUtils;

import java.util.List;
import java.util.Map;

import com.wenxt.base.userMaster.LM_MENU_USERS;

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
	
	LM_MENU_USERS getTransactionData(String tranId, String tableName);

	List<LM_PROG_FIELD_DEFN_NEW> getFrontFormDetails();

	List<LovToJsonDTO> lovToJson(String qm_QUERY, String string, String string2);
	
	List<LM_FRONT_FORM_FLD_DEFN> getFrontFormList(String screenCode, String screenName);

	List<LM_HEADER_INFO_FLD_DEFN> getHeaderInfoList(String string, String string2);

//	List<LM_STATIC_DETAILS_FLD_DEFN> getStaticDetailsList(String string, String string2);

	List<LM_ACCORDION_FLD_DEFN> getAccordionResult(String string, String string2);

	List<LM_ACCORDION_FLD_DEFN> getAccordionFields(String string, String string2);

	List<LM_ACCOTAB_FLD_DEFN> getAccoTabs(String string, String string2);

	List<LM_ACCORDION_FLD_DEFN> getAccordionFieldsDefn(String string, String string2);

	List<LM_ACCOTAB_FLD_DEFN> getAccordionSubTabs(String string, String string2);

	lt_policy getQuotationFields(String string, String string2, Object object);

	LM_MENU_USERS getUserFields(String string, String string2, Object object);

	List<MRVKeyValue> getMrvFetchList(Object object);

	LM_CUSTOMER getCustomerFields(String string, String string2, Object object);

}
