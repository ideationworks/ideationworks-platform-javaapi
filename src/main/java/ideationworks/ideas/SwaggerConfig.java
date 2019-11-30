package ideationworks.ideas;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER    = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = ".*";

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.any())
                                                      .paths(PathSelectors.regex("/ideas($|/.*)|" +
                                                                                         "/tags($|/.*)|" +
                                                                                         "/categories($|/.*)|" +
                                                                                         "/users/login"))
                                                      .build().apiInfo(apiEndPointsInfo())
                                                      .securitySchemes(Lists.newArrayList(apiKey()))
                                                      .securityContexts(Lists.newArrayList(securityContext()));

    }

    private ApiInfo apiEndPointsInfo() {

        return new ApiInfoBuilder().title("Ideation.works REST API")
                                   .description("Ideation.works REST API")
                                   .contact(new Contact("Matthew Davis", "https://ideation.works", "matthew@ideation.works"))
                                   .license("Apache License 2.0")
                                   .licenseUrl("https://choosealicense.com/licenses/apache-2.0")
                                   .version("1.0.0")
                                   .build();

    }

    private ApiKey apiKey() {

        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");

    }

    private SecurityContext securityContext() {

        return SecurityContext.builder()
                              .securityReferences(defaultAuth())
                              .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                              .build();

    }

    List<SecurityReference> defaultAuth() {

        AuthorizationScope   authorizationScope  = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;

        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));

    }

}
