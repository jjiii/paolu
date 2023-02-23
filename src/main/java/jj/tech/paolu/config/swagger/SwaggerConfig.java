package jj.tech.paolu.config.swagger;



import java.util.Collections;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * swagger配置
 * 加入分组功能(默认注释掉)
 *
 * @author Dou
 * @date 2022/6/30
 **/
@Configuration
public class SwaggerConfig {


    @Bean
    OpenAPI springShopOpenAPI() {
    	

        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.type(SecurityScheme.Type.HTTP)
                .scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
        //		  .scheme("basic");
        
        Components compoenents = new Components();
        compoenents.addSecuritySchemes("bearer-key", securityScheme);
       
        return new OpenAPI()
                .components(compoenents)
                .info(new Info().title("JJ  Doc 接口文档")
//	          .description("Spring shop sample application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .security(Collections.singletonList(new SecurityRequirement().addList("bearer-key")));
    }
    
    
    @Bean
    @Profile("prod")
    GroupedOpenApi prodApi(OpenAPI openAPI) {
        return GroupedOpenApi.builder()
                .group("user-test-api")
                .pathsToMatch("/null")
                .addOpenApiCustomizer(api -> {
                    api = openAPI;
                })
                .pathsToExclude("/health/*")
                .build();
    }


    @Bean
    @Profile("!prod")
    GroupedOpenApi loginApi(OpenAPI openAPI) {
    	String paths[] = {"/login"};
        return GroupedOpenApi.builder()
                .group("apiLogin")
//				.packagesToScan("tech.bcnew.modular.authaudit.controller")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(api -> {
                    api = openAPI;
                })
                .pathsToExclude("/health/*")
                .build();
    }
    
    @Bean
    @Profile("!prod")
    GroupedOpenApi jpaApi(OpenAPI openAPI) {
    	String packagesToscan[] = {"jj.tech.paolu.repository"};
        return GroupedOpenApi.builder()
                .group("jpa")
				.packagesToScan(packagesToscan)
                .addOpenApiCustomizer(api -> {
                    api = openAPI;
                })
                .pathsToExclude("/health/*")
                .build();
    }
    
    
    
    @Bean
    @Profile("!prod")
    GroupedOpenApi bizApi(OpenAPI openAPI) {
        return GroupedOpenApi.builder()
                .group("biz")
                .pathsToMatch("/*")
                .addOpenApiCustomizer(api -> {
                    api = openAPI;
                })
                .pathsToExclude("/health/*","/jpa*")
                .build();
    }


}