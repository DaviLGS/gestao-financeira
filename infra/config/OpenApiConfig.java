package financeira.gestao.demo.infra.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão Financeira API")
                        .version("1.0.0")
                        .description("API para gestão financeira, cotações e movimentações.")
                        .contact(new Contact()
                                .name("Seu Nome")
                                .email("seuemail@exemplo.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório do Projeto")
                        .url("https://github.com/seuusuario/seuprojeto"));
    }
}
