package CoBo.ItFarm.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI(){
                Info info = new Info()
                        .title("ITFarm Swagger")
                        .description("ITFarm 개발 Swagger입니다.")
                        .contact(new Contact().name("Seungkyu-Han").email("trust1204@gmail.com"));

                SecurityRequirement securityRequirement
                        = new SecurityRequirement().addList("Auth");

                Components components = new Components()
                        .addSecuritySchemes("Auth", new SecurityScheme()
                                .name("Auth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));


                return new OpenAPI()
                        .addSecurityItem(securityRequirement)
                        .components(components)
                        .info(info);

        }
}
