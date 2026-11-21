package br.com.corteAqui.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("CorteAqui")
                .version("1.0.0")
                .description("Documentação do CorteAqui API")
        ).addSecurityItem(new SecurityRequirement()
        );
    }
}
