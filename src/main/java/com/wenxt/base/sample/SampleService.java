package com.wenxt.base.sample;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

public interface SampleService {
	
	public String insertOrUpdate(InsertRequestModel insertRequest);
	
	public void save(ProgramFieldsModel field);
	
	public Optional<ProgramFieldsModel> get(Integer id);
	
	public void update(ProgramFieldsModel field);
	
	public List<ProgramFieldsModel> getList(String programCode);

}
