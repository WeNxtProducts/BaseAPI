package com.wenxt.base.sample;

import java.util.List;

public class InsertRequestModel {
	
	private Character insertFlag;
	
	private Integer recordId;
	
	private List<ProgramFieldsModel> records;

	public Character getInsertFlag() {
		return insertFlag;
	}

	public void setInsertFlag(Character insertFlag) {
		this.insertFlag = insertFlag;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public List<ProgramFieldsModel> getRecords() {
		return records;
	}

	public void setRecords(List<ProgramFieldsModel> records) {
		this.records = records;
	}

}
