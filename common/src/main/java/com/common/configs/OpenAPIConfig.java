package com.common.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "My API",
                version = "v1",
                description = "API documentation"
        ),
        servers = {
                @Server(url = "http://localhost:6061", description = "Identify Service")
        }
)
public class OpenAPIConfig {
}