package com.wenxt.base.sample;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SampleRepository extends CrudRepository<ProgramFieldsModel, Integer>{
	
	ProgramFieldsModel save(ProgramFieldsModel field);
	
	Optional<ProgramFieldsModel> findById(Integer id);
	
	List<ProgramFieldsModel> findByProgramCode(String programCode);
	

}
