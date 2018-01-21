package com.lucasgr7.hexagontest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicates;
import com.lucasgr7.hexagontest.util.ConfigInfo;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Profile("dev")
@EnableSwagger2
public class SwaggerConfig {

	
	@Bean
	public Docket postsApi() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("com.luizalabs.cfgdepara")
				.apiInfo(apiInfo()).select().paths(regex("/.*")).apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))      
	             .build(); 
	}

	private ApiInfo apiInfo() {
		ConfigInfo.Version = getClass().getPackage().getImplementationVersion();
		if(ConfigInfo.Version==null) {
			ConfigInfo.Version = "PRE-BUILD";
		}
		return new ApiInfoBuilder().title("API da prova Hexagon")
				.description("Cadastro de Vehicle e Vehicle Type")
				.contact("Lucas Ribeiro - lucasgr7@gmail.com")
				.version(ConfigInfo.Version).build();
	}
	
}