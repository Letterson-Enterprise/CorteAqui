package br.com.corteAqui.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    /**
     * Método para habilitar chave de API do JWT.
     * @Autor: Letícia Geovana
     * @Params: void
     * @Return: SecurityScheme
    **/
    private SecurityScheme chaveAPIScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    /**
     * Habilita a Documentação Swagger na aplicação.
     * @Autor: Letícia Geovana
     * @Params: void
     * @Return: OpenAPI
     **/
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("CorteAqui")
                .version("1.0.0")
                .description("Documentação do CorteAqui API")
        ).addSecurityItem(new SecurityRequirement()
                .addList("Barreira de Autenticação")
        ).components(new Components()
                .addSecuritySchemes("bearer",chaveAPIScheme())
        );
    }
}
