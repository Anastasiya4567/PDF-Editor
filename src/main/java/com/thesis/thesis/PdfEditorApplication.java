package com.thesis.thesis;

import com.thesis.thesis.application.ApplicationConfig;
import com.thesis.thesis.infractructure.InfrastructureConfig;
import com.thesis.thesis.interfaces.rest.InterfacesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({InfrastructureConfig.class, ApplicationConfig.class, InterfacesConfig.class})
public class PdfEditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfEditorApplication.class, args);
	}

}
