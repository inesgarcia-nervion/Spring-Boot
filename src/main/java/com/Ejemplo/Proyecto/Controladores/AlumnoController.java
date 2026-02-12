package com.Ejemplo.Proyecto.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Ejemplo.Proyecto.Entidades.Alumno;
import com.Ejemplo.Proyecto.Repositorios.AlumnoRepository;
import com.Ejemplo.Proyecto.DTO.AlumnoDTO;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/alumnos")
@Tag(name = "Alumnos", description = "Operaciones CRUD sobre alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository repo;

    // Devuelve todos los alumnos
    @GetMapping
    @Operation(summary = "Listar alumnos", description = "Devuelve la lista completa de alumnos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alumnos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Alumno.class)),
                            examples = @ExampleObject(value = "[{\"id\":1,\"nombre\":\"Inés García\",\"email\":\"usuario@gmail.com\"}]")))
    })
    public List<Alumno> listar() {
        return repo.findAll();
    }

    // Devuelve alumno por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener alumno por ID", description = "Retorna un alumno específico según su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno encontrado",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = Alumno.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"nombre\":\"Inés García\",\"email\":\"usuario@gmail.com\"}"))),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"Alumno no encontrado\"}")))
    })
    public Optional<Alumno> getIdAlumno(
            @Parameter(description = "ID del alumno a buscar", example = "1", required = true) 
            @PathVariable Long id) {
        return repo.findById(id);
    }

    // Crea alumno
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear alumno", description = "Registra un nuevo alumno en el sistema con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alumno creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = Alumno.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"nombre\":\"Inés García\",\"email\":\"usuario@gmail.com\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"email\":\"Email requerido\",\"nombre\":\"Nombre requerido\"}")))
    })
    public ResponseEntity<Alumno> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del alumno a crear", 
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlumnoDTO.class),
                            examples = @ExampleObject(value = "{\"nombre\":\"Inés García\",\"email\":\"usuario@gmail.com\"}")
                    )
            )
            @Valid @RequestBody AlumnoDTO dto) {
        Alumno a = new Alumno();
        a.setNombre(dto.getNombre());
        a.setEmail(dto.getEmail());
        Alumno saved = repo.save(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Actualiza alumno
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar alumno", description = "Modifica los datos de un alumno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = Alumno.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"nombre\":\"Nuevo Nombre\",\"email\":\"nuevo@gmail.com\"}"))),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"Alumno no encontrado\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"email\":\"Formato inválido\"}")))
    })
    public ResponseEntity<Alumno> actualizar(
            @Parameter(description = "ID del alumno a actualizar", example = "1", required = true) 
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del alumno", 
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlumnoDTO.class),
                            examples = @ExampleObject(value = "{\"nombre\":\"Nuevo Nombre\",\"email\":\"nuevo@gmail.com\"}")
                    )
            )
            @Valid @RequestBody AlumnoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setEmail(dto.getEmail());
            Alumno saved = repo.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Elimina alumno
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar alumno", description = "Elimina un alumno del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alumno eliminado exitosamente", 
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"Alumno no encontrado\"}")))
    })
    public ResponseEntity<Void> deleteAlumno(
            @Parameter(description = "ID del alumno a eliminar", example = "1", required = true) 
            @PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}