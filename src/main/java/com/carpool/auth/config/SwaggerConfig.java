package com.carpool.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
//@ComponentScan(basePackageClasses = {PetController.class})
public class SwaggerConfig {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo())
                .tags(new Tag("userController","Account control operations which can be done by users of the system"))
                .tags(new Tag("roleController","Operations pertaining to roles of users of the system"))
                .tags(new Tag("permissionController","Operations pertaining to permissions allowed in the applications"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.carpool.auth.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("AKSON CARPOOL AUTHORIZATION REST API")
                .description("REST API documentation for the service which used as authorization and  authentication server for carpool application")
                .contact(new Contact("AksonCab", "www.techshard.com", "techshard08@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0-SNAPSHOT")
                .build();
    }

}
