package com.thesis.thesis.infrastructure;

import com.thesis.thesis.infrastructure.adapter.mongo.MongoInfrastructureConfig;
import com.thesis.thesis.infrastructure.adapter.postgres.PostgresInfrastructureConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoInfrastructureConfig.class, PostgresInfrastructureConfig.class})
public class InfrastructureConfig {
}
