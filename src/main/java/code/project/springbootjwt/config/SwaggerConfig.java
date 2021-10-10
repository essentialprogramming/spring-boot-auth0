package code.project.springbootjwt.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /*
    Tasks API
     */
    @Bean
    public GroupedOpenApi weatherApi() {
        final String[] packagesToScan = {"code.project.springbootjwt.controller"};
        return GroupedOpenApi
                .builder()
                .group("Tasks API")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/tasks/**")
                .addOpenApiCustomiser(tasksApiCustomizer())
                .build();
    }

    private OpenApiCustomiser tasksApiCustomizer() {
        return openAPI -> openAPI
                .info(new Info()
                        .title("Tasks API")
                        .description("Tasks services using OpenAPI")
                        .version("3.0.0"));
    }

    @Bean
    public OpenAPI tasksOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Tasks API").description(
                        "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
    }

}