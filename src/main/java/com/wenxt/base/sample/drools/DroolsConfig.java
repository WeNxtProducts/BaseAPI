package com.wenxt.base.sample.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {
	
	private static final String drl_file_path = "rules/rules.drl";
	
	private static final KieServices kieServices = KieServices.Factory.get();
	
	@Bean
	public KieContainer kieContainer() {
        // Ensure correct configuration and rule loading
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        // Add your rules to the kieFileSystem
        kieFileSystem.write(ResourceFactory.newClassPathResource(drl_file_path));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        return kieContainer;
		
	}



}
