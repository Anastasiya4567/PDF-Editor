package com.pdf.editor.infrastructure;

import com.pdf.editor.infrastructure.adapter.mongo.MongoInfrastructureConfig;
import com.pdf.editor.infrastructure.adapter.postgres.PostgresInfrastructureConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoInfrastructureConfig.class, PostgresInfrastructureConfig.class})
public class InfrastructureConfig {
}
