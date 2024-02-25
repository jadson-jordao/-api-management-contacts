package com.contacts;


import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Gerenciamento de Contatos", version = "1.0",
		description = "Esta API tem como função realizar todas as operações de CRUD para uma entidade de contato"))
public class ApiManagementContactsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiManagementContactsApplication.class, args);
	}

}


