package com.wenxt.base.commonUtils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wenxt.base.userMaster.LM_MENU_USERS;


@Repository
public class CommonDaoImpl implements CommonDao {
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;	
	
	@Override
	public List<MenuResultDTO> getMenuList(String groupId, String query){
		 String sql = query;
		List<MenuResultDTO> result = template.query(sql,new Object[] {groupId}, new BeanPropertyRowMapper<>(MenuResultDTO.class));
		return result;
	}

	@Override
	public List<MenuResultDTO> getChildMenuList(String parentId, String groupId, String query) {
		 String sql = query;
		List<MenuResultDTO> result = template.query(sql,new Object[] {parentId, groupId}, new BeanPropertyRowMapper<>(MenuResultDTO.class));
		return result;
	}

	@Override
	public QUERY_MASTER getQueryLov(int queryId) {
		try {
	        String sql = "SELECT * FROM QUERY_MASTER WHERE QM_SYS_ID = ?";
	        QUERY_MASTER result = template.queryForObject(sql, new Object[] {queryId}, new BeanPropertyRowMapper<>(QUERY_MASTER.class));
	        return result;
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}

	@Override
	public List<QUERY_PARAM_MASTER> getQueryParams(int sysId) {
		String sql = "SELECT * FROM QUERY_PARAM_MASTER WHERE QPM_QM_SYS_ID = ? order by QPM_PARAM_TYPE desc";
		List<QUERY_PARAM_MASTER> result = template.query(sql, new Object[] {sysId}, new BeanPropertyRowMapper<>(QUERY_PARAM_MASTER.class));
		return result;
	}

	@Override
	public List<LOVDTO> executeLOVQuery(String query, Map<String, Object> paramsList) {
		if(paramsList == null || paramsList.size() <= 0) {
		List<LOVDTO> result = template.query(query, new BeanPropertyRowMapper<>(LOVDTO.class));
		return result;
		}
		else {
			List<LOVDTO> result = namedTemplate.query(query, paramsList, new BeanPropertyRowMapper<>(LOVDTO.class));
			return result;
		}
	}

	@Override
	public List<Map<String, Object>> getListingData(String query) {
		    List<Map<String, Object>> result = template.queryForList(query);
		    return result;
	}
	
	@Override
	public List<Map<String, Object>> getMrvListing(String query, Map<String, Object> paramsList) {
		if(paramsList.size() <= 0) {
	    List<Map<String, Object>> result = template.queryForList(query);
	    return result;
		}else {
			List<Map<String, Object>> result = namedTemplate.queryForList(query, paramsList);
			return result;
		}
	}

	@Override
	public List<LM_PROG_FIELD_DEFN_NEW> getFieldList(String screenCode, String screenName, String query) {
		String sql = query;
		List<LM_PROG_FIELD_DEFN_NEW> result = template.query(sql, new Object[] {screenCode, screenName}, new BeanPropertyRowMapper<>(LM_PROG_FIELD_DEFN_NEW.class));
		return result;
	}

	@Override
	public service_url_mapping getUrlData(AsyncDTO object) {
		String sql = "SELECT * FROM service_url_mapping WHERE serv_prog_code = ? AND serv_screen_name = ? AND serv_service_name = ?";
		service_url_mapping result = template.queryForObject(sql, new Object[] {object.getScreenCode(), object.getScreenName(), object.getServiceName()}, new BeanPropertyRowMapper<>(service_url_mapping.class));
		return result;
	}

	@Override
	public LM_MENU_USERS getTransactionData(String tranId, String tableName) {
		String sql = "SELECT * FROM " + tableName + " WHERE user_id = '" +  tranId + "'";
        LM_MENU_USERS result = template.queryForObject(sql, new BeanPropertyRowMapper<>(LM_MENU_USERS.class));
        return result;
	}
	
	@Override
	public List<LM_PROG_FIELD_DEFN_NEW> getFrontFormDetails(){
		String sql = "SELECT * FROM LM_PROG_FIELD_DEFN WHERE PFD_FORM_ITEM_TYPE1 = 'FrontFormField' AND PFD_PROG_CODE = 'USERMASTER' AND PFD_SCREEN_NAME = 'USERCREATE'";
		List<LM_PROG_FIELD_DEFN_NEW> result = template.query(sql, new BeanPropertyRowMapper<>(LM_PROG_FIELD_DEFN_NEW.class));
		return result;
	}

}
