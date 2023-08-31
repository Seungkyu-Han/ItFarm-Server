package CoBo.ItFarm.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "ItFarm",
                description = "ItFarm 개발 Swagger"
        ))
@Configuration
public class SwaggerConfig {

}
