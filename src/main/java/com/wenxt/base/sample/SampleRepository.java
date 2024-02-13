package com.wenxt.base.sample;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SampleRepository extends CrudRepository<ProgramFieldModel, Integer>{
	
	ProgramFieldModel save(ProgramFieldModel field);
	
	Optional<ProgramFieldModel> findById(Integer id);
	
	List<ProgramFieldModel> findByProgramCode(String programCode);
	

}
