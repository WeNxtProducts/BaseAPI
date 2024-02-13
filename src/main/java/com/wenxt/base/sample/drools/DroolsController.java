package com.wenxt.base.sample.drools;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/drools")
public class DroolsController {
	
	@Autowired
	private KieContainer kieContainer;
	
	@Autowired
	private DroolsService service;
	
	@PostMapping("/details")
	public DetailsModel saveDetails(@RequestBody DetailsModel detailsModel) {
		
		KieSession kieSession = kieContainer.newKieSession();

		try {
		        kieSession.insert(detailsModel);
		        kieSession.fireAllRules();
		    } finally {
		        kieSession.dispose();
		    }
		    return detailsModel;
	}
	
	@GetMapping("/test")
	public DetailsModel test(@RequestBody DetailsModel detailsModel) {
		return service.test(detailsModel);
	}

}
