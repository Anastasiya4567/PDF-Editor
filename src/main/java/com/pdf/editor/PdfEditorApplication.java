package com.pdf.editor;

import com.pdf.editor.interfaces.rest.InterfacesConfig;
import com.pdf.editor.application.ApplicationConfig;
import com.pdf.editor.application.domain.DocumentConfiguration;
import com.pdf.editor.infrastructure.InfrastructureConfig;
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
@ComponentScan(basePackages = {"com.pdf.editor", "kernel.auth"})
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories("kernel.auth.repository")
@EntityScan("kernel.auth.model")
public class PdfEditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfEditorApplication.class, args);
    }

}
