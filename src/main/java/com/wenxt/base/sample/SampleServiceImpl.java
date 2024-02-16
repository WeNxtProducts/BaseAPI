package com.wenxt.base.sample;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SampleServiceImpl implements SampleService{
	
	@Autowired
	private SampleRepository repo;
	
	@Autowired
	private SampleDao sampleDao;
	
	@Transactional
	public String insertOrUpdate(InsertRequestModel insertRequest) {
		if(insertRequest.getInsertFlag().equals('Y')) {
		insertRequest.getRecords().stream().forEach(programField -> {
			sampleDao.insertOrUpdate(insertRequest, programField.getTableName(), insertRequest.getRecordId());
		});
		}
		else {
			insertRequest.getRecords().stream().forEach(programField -> {
				sampleDao.insertOrUpdate(insertRequest, programField.getTableName(), insertRequest.getRecordId());
			});
		}
		
		return "SUCCESS";
	}

	public void save(ProgramFieldsModel field) {
		// TODO Auto-generated method stub
		repo.save(field);
	}
	
	public Optional<ProgramFieldsModel> get(Integer id) {
		Optional<ProgramFieldsModel> model = repo.findById(id);
		return model;
	}
	
	@Transactional
	@Modifying
	public void update(ProgramFieldsModel field) {
		Optional<ProgramFieldsModel> existingModel = repo.findById(field.getSysId());
		 existingModel.ifPresent(model -> {
	            model.setDataType("N");
	            model.setPfd_fld_name(field.getFieldName());
	            model.setPfd_pb_blk_name(field.getBlockName());
	            repo.save(model);
	        });
	}
	
	public List<ProgramFieldsModel> getList(String programCode){
		return repo.findByProgramCode(programCode);
	}


}
