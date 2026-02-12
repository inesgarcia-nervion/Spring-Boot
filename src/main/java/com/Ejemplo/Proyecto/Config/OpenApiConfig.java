package com.Ejemplo.Proyecto.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Proyecto API",
                version = "v1",
                description = "API para gestionar alumnos",
                contact = @Contact(name = "Equipo", email = "soporte@example.com")
        ),
        servers = {@Server(url = "/")}
)
public class OpenApiConfig {

}
