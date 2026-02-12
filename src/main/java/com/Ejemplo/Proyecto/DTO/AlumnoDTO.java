package com.Ejemplo.Proyecto.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnoDTO", description = "Datos para crear o actualizar un alumno")
public class AlumnoDTO {

    @Schema(description = "Correo electrónico (debe ser gmail.com)", example = "usuario@gmail.com")
    @NotNull(message = "Email requerido")
    @NotBlank(message = "Email no vacío")
    @Email(message = "Formato inválido")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Debe ser un correo gmail.com")
    private String email;

    @Schema(description = "Nombre completo del alumno", example = "Inés García")
    @NotNull(message = "Nombre requerido")
    @NotBlank(message = "Nombre no puede estar vacío")
    private String nombre;

    public AlumnoDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}