package com.wenxt.base.sample;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

public interface SampleService {
	
	public String insertOrUpdate(InsertRequestModel insertRequest);
	
	public void save(ProgramFieldModel field);
	
	public Optional<ProgramFieldModel> get(Integer id);
	
	public void update(ProgramFieldModel field);
	
	public List<ProgramFieldModel> getList(String programCode);

}
