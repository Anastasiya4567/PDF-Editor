package com.thesis.thesis;

import com.thesis.thesis.application.ApplicationConfig;
import com.thesis.thesis.application.domain.DocumentConfiguration;
import com.thesis.thesis.infrastructure.InfrastructureConfig;
import com.thesis.thesis.interfaces.rest.InterfacesConfig;
import kernel.auth.config.AppProperties;
import kernel.auth.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import({InfrastructureConfig.class, ApplicationConfig.class, InterfacesConfig.class, DocumentConfiguration.class, SecurityConfig.class})
@ComponentScan(basePackages = {"com.thesis.thesis", "kernel.auth"})
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories("kernel.auth.repository")
@EntityScan("kernel.auth.model")
public class PdfEditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfEditorApplication.class, args);
    }

}
