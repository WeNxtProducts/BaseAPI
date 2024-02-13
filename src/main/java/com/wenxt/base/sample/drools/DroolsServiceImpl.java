package com.wenxt.base.sample.drools;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsServiceImpl implements DroolsService {
	
	@Autowired
	private KieContainer kieContainer;

	@Override
	public DetailsModel test(DetailsModel detailsModel) {
		// TODO Auto-generated method stub
		KieSession kieSession = kieContainer.newKieSession();

		try {
		        kieSession.insert(detailsModel);
		        kieSession.fireAllRules();
		    } finally {
		        kieSession.dispose();
		    }
		    return detailsModel;
	}



}
