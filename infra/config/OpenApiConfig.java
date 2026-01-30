package financeira.gestao.demo.infra.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão Financeira")
                        .version("1.0.0")
                        .description("API para gestão financeira e movimentações.")
                        .contact(new Contact()
                                .name("Davi Garcia")
                                .email("davilessagarcia@gmail.com"))
                )

                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )

                .externalDocs(new ExternalDocumentation()
                        .description("Repositório do Projeto")
                        .url("https://github.com/Davi_LGS/gestao-financeira"));
    }
}
