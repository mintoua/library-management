package com.syniiq.library_management.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(
                        name = "SYNIIQ",
                        email = "contact@syniiq.com",
                        url = "https://syniiq.com"
                ),
                title = "LIBRARY MANAGEMENT APIs",
                description = "Those APIs created by SYNIIQ for Library Management",
                termsOfService = "&copy; SYNIIQ",
                version = "v1"
        )
)
public class OpenApiConfig {

}