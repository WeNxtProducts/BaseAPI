package com.wenxt.base.sample;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/records")
public class SampleController {
	
	@Autowired
	private SampleService sampleService;
	
	 //JDBCTEMPLATE
	 @PostMapping("/insertOrUpdate")
	 public ResponseEntity<String> testSave(@RequestBody InsertRequestModel insertRequest){
		 return new ResponseEntity<>(sampleService.insertOrUpdate(insertRequest), HttpStatus.OK);
	 }
	
	 //JPA SAVE Method
	 @PostMapping("/sampSave")
	 public void saveRepo(@RequestBody ProgramFieldModel field) {
		 sampleService.save(field);
	 }
	 
	 //JPA GET Method
	 @GetMapping("/sampGet")
	 public Optional<ProgramFieldModel> sampGet(@RequestParam Integer sysId) {
		return sampleService.get(sysId);
	 }
	 
	 //JPA UPDATE Method
	 @PostMapping("/sampUpdate")
	 public void sampUpdate(@RequestBody ProgramFieldModel field) {
		 sampleService.update(field);
	 }
	 
	 //JPA LIST Method
	 @GetMapping("/sampListGet")
	 public List<ProgramFieldModel> sampListGet(@RequestParam String programCode) {
		 return sampleService.getList(programCode);
	 }

}
