package com.wenxt.base.commonUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wenxt.base.userMaster.LM_MENU_USERS;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private AppAuditRepository auditRepo;

	@Autowired
	private AppExceptionRepository exceptionRepo;

	@Autowired
	private CommonDao commonDao;

	@Value("${insert.audit.log}")
	private String saveAuditMessage;

	@Value("${spring.success.code}")
	private String successCode;

	@Value("${spring.error.code}")
	private String errorCode;

	@Value("${spring.warning.code}")
	private String warningCode;

	@Value("${spring.message.code}")
	private String messageCode;

	@Value("${spring.status.code}")
	private String statusCode;

	@Value("${spring.data.code}")
	private String dataCode;
	
	@Value("${spring.project.basePath}")
	private String basePath;

	@Value("${spring.project.baseUrl}")
	private String getBaseURL;

	@Value("${get.audit.log}")
	private String getAuditMessage;

	@Value("${get.audit.empty}")
	private String getEmptyAuditMessage;

	@Value("${insert.exception.details}")
	private String insertExceptionMessage;

	@Value("${get.exception.details}")
	private String getExceptionMessage;

	@Value("${get.exception.empty}")
	private String getExceptionEmptyMessage;

	@Value("${spring.lov.worngid}")
	private String getLovWrongId;

	@Override
	public String insertAudit(APP_AUDIT auditModel) {
		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String hostName = localHost.getHostName();
			String ipAddress = localHost.getHostAddress();

			String userAgentString = "User-Agent";

			UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

			Browser browser = userAgent.getBrowser();

			String browserName = browser.getName();

			auditModel.setAAD_Host_Info(hostName + ", " + ipAddress + ", " + browserName);
			auditModel.setAAD_Cr_Date(LocalDateTime.now());
		} catch (Exception e) {
			e.printStackTrace();
		}
		APP_AUDIT savedModel = auditRepo.save(auditModel);
		response.put(statusCode, successCode);
		response.put(messageCode, saveAuditMessage);
		data.put("Id", savedModel.getAAD_Id());
		response.put(dataCode, data);

		return response.toString();
	}

	@Override
	public String getAuditByUserAndScreen(APP_AUDIT auditModel) {
		JSONObject response = new JSONObject();
		List<APP_AUDIT> list = auditRepo.findBy_AAD_User_IdAnd_AAD_Screen_Name(auditModel.getAAD_User_Id(),
				auditModel.getAAD_Screen_Name());
		if (list.size() > 0) {
			response.put(statusCode, successCode);
			response.put(messageCode, getAuditMessage);
			response.put(dataCode, list);
		} else {
			response.put(statusCode, successCode);
			response.put(messageCode, getEmptyAuditMessage);
		}

		return response.toString();
	}

	public String insertException(ExceptionDTO exceptionDTO) {
		JSONObject response = new JSONObject();
		JSONObject data = new JSONObject();
		try {
			APP_EXCEPTIONS exceptionEntity = new APP_EXCEPTIONS();
			exceptionEntity.setScreenName(exceptionDTO.getScreenName());
			exceptionEntity.setServiceName(exceptionDTO.getServiceName());
			exceptionEntity.setUserID(exceptionDTO.getUserID());
			exceptionEntity.setTransactionNumber(exceptionDTO.getTransactionNumber());
			exceptionEntity.setFileOrModuleName(exceptionDTO.getFileOrModuleName());
			exceptionEntity.setExceptionMessage(exceptionDTO.getExceptionMessage());

			LocalDateTime currentDateTime = LocalDateTime.now();
			exceptionEntity.setDateTime(currentDateTime.toString());

			String hostInfo = "";
			try {
				InetAddress localhost = InetAddress.getLocalHost();
				String hostname = localhost.getHostName();
				String ipAddress = localhost.getHostAddress();

				String userAgentString = "User-Agent";

				UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

				Browser browser = userAgent.getBrowser();

				String browserName = browser.getName();

				hostInfo = hostname + "," + ipAddress + "," + browserName;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			exceptionEntity.setHostInfo(hostInfo);

			APP_EXCEPTIONS app = exceptionRepo.save(exceptionEntity);

			response.put(statusCode, successCode);
			response.put(messageCode, insertExceptionMessage);
			data.put("Id", app.getSno());
			response.put(dataCode, data);

			return response.toString();
		} catch (Exception e) {
			response.put(statusCode, warningCode);
			response.put(messageCode, e.getMessage());
			return response.toString();
		}
	}

	public String getAllExceptions() {
		JSONObject response = new JSONObject();
		List<APP_EXCEPTIONS> resultList = exceptionRepo.findAll();
		if (resultList.size() > 0) {
			response.put(statusCode, successCode);
			response.put(messageCode, getExceptionMessage);
			response.put(dataCode, resultList);
			return response.toString();
		}
		response.put(statusCode, errorCode);
		response.put(messageCode, getExceptionEmptyMessage);
		return response.toString();
	}

	@Override
	public String getMenuList(String groupId, HttpServletRequest request) {
		JSONObject response = new JSONObject();
		QUERY_MASTER query = commonDao.getQueryLov(14);
		List<MenuResultDTO> list = (List<MenuResultDTO>) commonDao.getMenuList(groupId, query.getQM_QUERY());
		List<MenuResultDTO> finalResult = new ArrayList<>();
		List<MenuResultDTO> child = new ArrayList<>();
		QUERY_MASTER childQuery = commonDao.getQueryLov(15);
		if (list.size() >= 1) {
			response.put(statusCode, successCode);
			response.put(messageCode, "Menu List Fetched Successfully");
			for (MenuResultDTO data : list) {
				if (data.getMenuParentId().equals("*")) {
					child = (List<MenuResultDTO>) commonDao.getChildMenuList(data.getMenuId(), groupId,
							childQuery.getQM_QUERY());
					if (child.size() >= 1) {
						data.setChildrens(child);
						finalResult.add(data);
					} else {
						finalResult.add(data);
					}
				}
			}
			response.put("Data", finalResult);
		}
		return response.toString();
	}

	@Override
	public String getQueryLOV(HttpServletRequest request) {
		Map<String, Object> params = processParamLOV(null, request);
		/*
		 * Getting query Id From the query string in a variable and removing it from
		 * params map
		 */
		int queryId = Integer.parseInt(((String) params.get("queryId")));
		params.remove("queryId");
		JSONObject response = new JSONObject();
		QUERY_MASTER query = commonDao.getQueryLov(queryId);
		if (query != null) {
			if (query.getQM_QUERY_TYPE().equals("lov")) {
				List<LOVDTO> queryResult = commonDao.executeLOVQuery(query.getQM_QUERY(), new HashMap());
				response.put(statusCode, successCode);
				response.put(dataCode, queryResult);
			} else if (query.getQM_QUERY_TYPE().equals("paramlov")) {
				/*
				 * getting parameters for the query if it is of type paramlov and processing it
				 * to a map
				 */
				List<QUERY_PARAM_MASTER> queryParams = commonDao.getQueryParams(query.getQM_SYS_ID());
				Map<String, Object> paramsMap = processParamLOV(queryParams, request);
				paramsMap.remove("queryId");
				List<LOVDTO> queryResult = commonDao.executeLOVQuery(query.getQM_QUERY(), paramsMap);
				response.put(statusCode, successCode);
				response.put(dataCode, queryResult);
			}
		} else {
			response.put(statusCode, errorCode);
			response.put(messageCode, getLovWrongId);
		}
		return response.toString();
	}

	private Map<String, Object> processParamLOV(List<QUERY_PARAM_MASTER> queryParams, HttpServletRequest request) {
		Map<String, Object> parameters = new HashMap<>();
		if (queryParams != null) {
			for (QUERY_PARAM_MASTER params : queryParams) {
				if (params.getQPM_PARAM_TYPE().equals("S")) {
					parameters.put(params.getQPM_PARAM_NAME(), params.getQPM_PARAM_VALUE());
				} else if (params.getQPM_PARAM_TYPE().equals("P")) {
					String queryString = request.getQueryString();
					if (queryString != null) {
						for (String keyValue : queryString.split("&")) {
							String[] parts = keyValue.split("=");
							if (parts.length == 2) {
								parameters.put(parts[0], parts[1]);
							}
						}
					}
					return parameters;
				}
			}
		} else {
			String queryString = request.getQueryString();
			if (queryString != null) {
				for (String keyValue : queryString.split("&")) {
					String[] parts = keyValue.split("=");
					if (parts.length == 2) {
						parameters.put(parts[0], parts[1]);
					}
				}
			}
			return parameters;
		}
		return parameters;
	}

	@Override
	public String getQueryParamLOV(HttpServletRequest request) {
		Map<String, Object> params = processParamLOV(null, request);
		int queryId = Integer.parseInt(((String) params.get("queryId")));
		params.remove("queryId");
		JSONObject response = new JSONObject();
		QUERY_MASTER query = commonDao.getQueryLov(queryId);
		if (query != null) {
//			if (query.getQM_QUERY_TYPE().equals("lov")) {
//				List<LOVDTO> queryResult = commonDao.executeLOVQuery(query.getQM_QUERY(), new HashMap());
//				response.put(statusCode, successCode);
//				response.put(dataCode, queryResult);
//			} else 
				if (query.getQM_QUERY_TYPE().equals("paramlov")) {
				List<QUERY_PARAM_MASTER> queryParams = commonDao.getQueryParams(query.getQM_SYS_ID());
				Map<String, Object> paramsMap = processParamLOV(queryParams, request);
				paramsMap.remove("queryId");
				List<LOVDTO> queryResult = commonDao.executeLOVQuery(query.getQM_QUERY(), paramsMap);
				response.put(statusCode, successCode);
				response.put(dataCode, queryResult);
			}
		} else {
			response.put(statusCode, errorCode);
			response.put(messageCode, getLovWrongId);
		}
		return response.toString();
	}

	@Override
	public String getListingData(HttpServletRequest request) {
		JSONObject response = new JSONObject();
		Map<String, Object> params = processParamLOV(null, request);
		int queryId = Integer.parseInt(((String) params.get("queryId")));
		params.remove("queryId");
		QUERY_MASTER query = commonDao.getQueryLov(queryId);
		if (query != null) {
			List<Map<String, Object>> queryResult = commonDao.getListingData(query.getQM_QUERY());
			Map<String, Object> firstRow = queryResult.get(0);
			Set<String> columnNames = firstRow.keySet();
			JSONObject heading = new JSONObject();
			String headString = (String) firstRow.get("Head");

			String[] headingNames = headString.split(",");

			for (String headingName : headingNames) {
				heading.put(headingName.trim(), headingName.trim());
			}
			queryResult.get(0).remove("Head");

			response.put("Heading", heading);
			response.put(statusCode, successCode);
			response.put(dataCode, queryResult);
		} else {
			response.put(statusCode, errorCode);
			response.put(messageCode, getLovWrongId);
		}
		return response.toString();
	}

	@Override
	public String getFieldList(HttpServletRequest request) {
		JSONObject response = new JSONObject();
		JSONObject headerInfo = new JSONObject();
		JSONObject staticDetailsFormFields = new JSONObject();
		JSONObject frontFormFields = new JSONObject();
		JSONObject accordions = new JSONObject();

		Map<String, Object> parametermap = processParamLOV(null, request);

		QUERY_MASTER exeQuery = commonDao.getQueryLov(16);

		List<LM_PROG_FIELD_DEFN_NEW> result = commonDao.getFieldList(parametermap.get("screenCode").toString(),
				parametermap.get("screenName").toString(), exeQuery.getQM_QUERY());
		if (result.size() > 0) {
			response.put("Header Info", headerInfo);
			result.stream().collect(
					Collectors.toMap(x -> x.getPFD_COLUMN_NAME(), x -> x, (existing, replacement) -> existing));

			Map<String, LM_PROG_FIELD_DEFN_NEW> staticDetailsMap = new LinkedHashMap<>();
			Map<String, LM_PROG_FIELD_DEFN_NEW> frontFormMap = new LinkedHashMap<>();

			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getPFD_FORM_ITEM_TYPE1().equals("Static")) {
					staticDetailsMap.put(result.get(i).getPFD_COLUMN_NAME(), result.get(i));
				} else if (result.get(i).getPFD_FORM_ITEM_TYPE1().equals("FrontForm")) {
					frontFormFields.put("Label", result.get(i).getPFD_FLD_NAME());
					frontFormMap = result.stream()
							.filter(item -> item.getPFD_FORM_ITEM_TYPE1().equals("FrontFormField"))
//							.peek(item -> {
//								if (item.getPFD_DATA_TYPE() != null && item.getPFD_DATA_TYPE().equals("lov")
//										|| item.getPFD_DATA_TYPE() != null
//												&& item.getPFD_DATA_TYPE().equals("searchlov")) {
//									QUERY_MASTER query = commonDao.getQueryLov(Integer.parseInt(item.getPFD_PARAM_1()));
//									List<LOVDTO> list = commonDao.executeLOVQuery(query.getQM_QUERY(), null);
//									item.setOptions(list);
//								} else 
//									if (item.getPFD_DATA_TYPE() != null
//										&& item.getPFD_DATA_TYPE().equals("paramlov")) {
//									QUERY_MASTER query = commonDao.getQueryLov(Integer.parseInt(item.getPFD_PARAM_1()));
//									List<QUERY_PARAM_MASTER> queryParams = commonDao
//											.getQueryParams(query.getQM_SYS_ID());
//									Map<String, Object> processedParams = processParamLOV(queryParams, request);
//									List<LOVDTO> list = commonDao.executeLOVQuery(query.getQM_QUERY(), processedParams);
//									item.setOptions(list);
//								}
//							})
							.collect(Collectors.toMap(LM_PROG_FIELD_DEFN_NEW::getPFD_COLUMN_NAME, Function.identity()));
				} else if (result.get(i).getPFD_FORM_ITEM_TYPE1().equals("AccordionHeader")) {
					JSONObject accordionResponse = new JSONObject();
					accordionResponse.put("Label", result.get(i).getPFD_FLD_NAME());
					accordions.put(result.get(i).getPFD_FLD_NAME(), parseAccordion(result.get(i), result, request));
				}
			}
			staticDetailsFormFields.put("Form_Fields", staticDetailsMap);
			response.put("Static_Details", staticDetailsFormFields);
			frontFormFields.put("Form_Fields", frontFormMap);
			response.put("Front_Form", frontFormFields);
			response.put("Accordions", accordions);
		}
		return response.toString();
	}

	@Override
	@Async
	public void asyncMethod(AsyncDTO object, HttpServletRequest request) {

		service_url_mapping urlData = commonDao.getUrlData(object);

		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + urlData.getserv_url();
		String jsonString = object.getObject();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String response = responseEntity.getBody();
		} else {
			throw new UsernameNotFoundException("JUST CHECK");
		}
	}

	@Override
	public void syncMethod(AsyncDTO inputRequest, HttpServletRequest request) {
		service_url_mapping urlData = commonDao.getUrlData(inputRequest);

		RestTemplate restTemplate = new RestTemplate();
		String url = getBaseURL + urlData.getserv_url();
		String jsonString = inputRequest.getObject();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			String response = responseEntity.getBody();
		} else {
			throw new UsernameNotFoundException("JUST CHECK");
		}
	}

	public Object parseAccordion(LM_PROG_FIELD_DEFN_NEW input, List<LM_PROG_FIELD_DEFN_NEW> result,
			HttpServletRequest request) {
		JSONObject response = new JSONObject();
		response.put("Label", input.getPFD_COLUMN_NAME());
		JSONObject fieldType = new JSONObject();
		JSONObject tabs = new JSONObject();
		String typeOfAccordion = "";
		Map<String, LM_PROG_FIELD_DEFN_NEW> fieldsMap = new LinkedHashMap<>();
		List<Map<String, LM_PROG_FIELD_DEFN_NEW>> resultFieldsMap = new ArrayList<>();
		List<LM_PROG_FIELD_DEFN_NEW> accordionFields = result.stream()
				.filter(object -> object.getPFD_FORM_ITEM_TYPE2() != null
						&& object.getPFD_FORM_ITEM_TYPE2().equals(input.getPFD_FLD_NAME()))
				.collect(Collectors.toList());
		for (int i = 0; i < accordionFields.size(); i++) {
			tabs = new JSONObject();
			tabs.put("Label", accordionFields.get(i).getPFD_FLD_NAME());
			typeOfAccordion = accordionFields.get(i).getPFD_FORM_ITEM_TYPE1();
			if (accordionFields.get(i).getPFD_FORM_ITEM_TYPE1().equals("AccordionTab")
					|| accordionFields.get(i).getPFD_FORM_ITEM_TYPE1().equals("AccordionForm")) {
				String accordionFieldName = accordionFields.get(i).getPFD_FLD_NAME();
				List<LM_PROG_FIELD_DEFN_NEW> accordFieldsList = result.stream()
						.filter(object -> object.getPFD_FORM_ITEM_TYPE2() != null
								&& object.getPFD_FORM_ITEM_TYPE2().equals(accordionFieldName))
						.collect(Collectors.toList());
				fieldsMap = accordFieldsList.stream()
						.filter(item -> item.getPFD_FORM_ITEM_TYPE2().equals(accordionFieldName))
//						.peek(item -> {
//							if (item.getPFD_DATA_TYPE() != null && item.getPFD_DATA_TYPE().equals("lov")) {
//								QUERY_MASTER query = commonDao.getQueryLov(Integer.parseInt(item.getPFD_PARAM_1()));
//								List<LOVDTO> list = commonDao.executeLOVQuery(query.getQM_QUERY(), null);
//								item.setOptions(list);
//							} else if (item.getPFD_DATA_TYPE() != null && item.getPFD_DATA_TYPE().equals("paramlov")) {
//								QUERY_MASTER query = commonDao.getQueryLov(Integer.parseInt(item.getPFD_PARAM_1()));
//								List<QUERY_PARAM_MASTER> queryParams = commonDao.getQueryParams(query.getQM_SYS_ID());
//								Map<String, Object> processedParams = processParamLOV(queryParams, request);
//								List<LOVDTO> list = commonDao.executeLOVQuery(query.getQM_QUERY(), processedParams);
//								item.setOptions(list);
//							}
//						})
						.collect(Collectors.toMap(LM_PROG_FIELD_DEFN_NEW::getPFD_COLUMN_NAME, Function.identity()));
				resultFieldsMap.add(fieldsMap);
				tabs.put("Form_Fields", fieldsMap);
				fieldType.put(accordionFields.get(i).getPFD_FLD_NAME(), tabs);
			} else if (accordionFields.get(i).getPFD_FORM_ITEM_TYPE1().equals("AccordionMrv")) {
				RestTemplate restTemplate = new RestTemplate();
				String url = getBaseURL + "common/getMrvListing?queryId=" + accordionFields.get(i).getPFD_SEQ_NO() + "&"
						+ request.getQueryString();
				ObjectMapper objectMapper = new ObjectMapper();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> requestEntity = new HttpEntity<>(headers);
				ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					String mrvResponse = responseEntity.getBody();
					try {
						objectMapper = new ObjectMapper();
						fieldsMap = objectMapper.readValue(mrvResponse, Map.class);
						fieldsMap.remove("Status");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					throw new UsernameNotFoundException("JUST CHECK");
				}
				fieldType.put(accordionFields.get(i).getPFD_FLD_NAME(), fieldsMap);
			}
		}
		if (typeOfAccordion.equals("AccordionTab")) {
			response.put("Tabs", fieldType);
		} else if (typeOfAccordion.equals("AccordionForm")) {
			response.put("Forms", fieldType);
		} else if (typeOfAccordion.equals("AccordionMrv")) {
			response.put("Mrv", fieldType);
		}
		return response;
	}

	@Override
	public String getMrvListing(HttpServletRequest request) {
		JSONObject response = new JSONObject();
		Map<String, Object> parames = processParamLOV(null, request);
		int queryId = Integer.parseInt(((String) parames.get("queryId")));
		parames.remove("queryId");
		QUERY_MASTER query = commonDao.getQueryLov(queryId);
		List<QUERY_PARAM_MASTER> queryParams = commonDao.getQueryParams(query.getQM_SYS_ID());
		Map<String, Object> parameters = new HashMap<>();

		Map<String, Object> parameterss = processParamLOV(null, request);
		for (QUERY_PARAM_MASTER params : queryParams) {
			if (params.getQPM_PARAM_TYPE().equals("S")) {
				parameters.put(params.getQPM_PARAM_NAME(), params.getQPM_PARAM_VALUE());
			} else if (params.getQPM_PARAM_TYPE().equals("P")) {
				if (parameterss.get(params.getQPM_PARAM_NAME()) != null) {
					parameters.put(params.getQPM_PARAM_NAME(), parameterss.get(params.getQPM_PARAM_NAME()));
				} else {
					parameters.put(params.getQPM_PARAM_NAME(), null);
				}
			}
		}
		List<Map<String, Object>> queryResult = commonDao.getMrvListing(query.getQM_QUERY(), parameters);
		Map<String, Object> firstRow = queryResult.get(0);
		Set<String> columnNames = firstRow.keySet();
		JSONObject heading = new JSONObject();
		String headString = (String) firstRow.get("Head");

		String[] headingNames = headString.split(",");

		for (String headingName : headingNames) {
			heading.put(headingName.trim(), headingName.trim());
		}
		queryResult.get(0).remove("Head");

		response.put("Heading", heading);
		response.put(statusCode, successCode);
		response.put(dataCode, queryResult);

		return response.toString();
	}

	@Override
	public void saveFieldDefJson(HttpServletRequest request) {
		String filePath = "D:\\newFile.txt";

		File file = new File(filePath);
		file.getParentFile().mkdirs();

		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8098/common/getfield?screenCode=USERMASTER&screenName=USERCREATE&mucd_user_id=ADMIN";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				String serviceResponse = responseEntity.getBody();
				bufferedWriter.write(serviceResponse);
			} else {
				throw new UsernameNotFoundException("JUST CHECK");
			}

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void serviceToJson(HttpServletRequest request) {
		AsyncDTO input = new AsyncDTO();

		Map<String, Object> params = processParamLOV(null, request);
		input.setScreenCode((String) params.get("screenCode"));
		input.setScreenName((String) params.get("screenName"));
		input.setServiceName((String) params.get("serviceName"));

		service_url_mapping object = commonDao.getUrlData(input);
		String filePath = object.getserv_response() + object.getserv_screen_name() + "_" + object.getserv_type()
				+ ".json";

		File file = new File(filePath);
		file.getParentFile().mkdirs();

		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			RestTemplate restTemplate = new RestTemplate();
			String url = getBaseURL + object.getserv_url() + "?" + "screenCode=" + params.get("screenCode")
					+ "&screenName=" + params.get("screenName");
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				String serviceResponse = responseEntity.getBody();
				bufferedWriter.write(serviceResponse);
			} else {
				throw new UsernameNotFoundException("JUST CHECK");
			}

			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String editFields(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		Map<String, Object> params = processParamLOV(null, request);
		try {
//			Map<String, Object> test;
//				test = mapper.readValue(new File("D:\\Common_getFieldList.json"), 
//							new TypeReference<Map<String, Object>>() {});
//		 	List<LM_PROG_FIELD_DEFN_NEW> object = commonDao.getFrontFormDetails(); 
			JsonNode innerNode = null;
			JsonNode node = mapper
					.readTree(new File("D:\\" + params.get("screenName") + "_" + params.get("serviceName") + ".json"));

			String tableName = ((ObjectNode) node).get("Front_Form").get("Form_Fields").get("user_initial")
					.get("PFD_TABLE_NAME").toString();
			LM_MENU_USERS users = commonDao.getTransactionData((String) params.get("tranId"),
					tableName.substring(1, tableName.length() - 1));
			for (int i = 0; i < users.getClass().getDeclaredFields().length; i++) {
				Field field = users.getClass().getDeclaredFields()[i];
				field.setAccessible(true);
				Object value;
				try {
					value = field.get(users);
					String fieldName = field.getName();
					if (((ObjectNode) node).get("Front_Form").get("Form_Fields").get(fieldName) != null) {
						innerNode = ((ObjectNode) node).get("Front_Form").get("Form_Fields").get(fieldName);
						((ObjectNode) innerNode).putPOJO("PFD_FLD_VALUE", value);
					} else if (((ObjectNode) node).get("Static_Details").get("Form_Fields").get(fieldName) != null) {
						innerNode = ((ObjectNode) node).get("Front_Form").get("Form_Fields").get(fieldName);
						((ObjectNode) innerNode).putPOJO("PFD_FLD_VALUE", value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return node.toString();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataCode;

	}

	@Override
	public void lovToJson(HttpServletRequest request) {
		JSONObject response = new JSONObject();
		Map<String, Object> params = processParamLOV(null, request);
		Map<String, List<LOVDTO>> resultMap = new HashMap<>();

		String file_path = basePath + params.get("screenName") + "getLOVList.json";
		QUERY_MASTER query = commonDao.getQueryLov(18);
		if (query != null) {
			List<LovToJsonDTO> result = commonDao.lovToJson(query.getQM_QUERY(), params.get("screenCode").toString(),
					params.get("screenName").toString());
			for (LovToJsonDTO item : result) {
				resultMap.put(item.getColumnName(), commonDao.executeLOVQuery(item.getQuery(), null));
			}
			try {
				File file = new File(file_path);
				file.getParentFile().mkdirs();
				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				response.put("Result", resultMap);
				bufferedWriter.write(response.get("Result").toString());
				bufferedWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
