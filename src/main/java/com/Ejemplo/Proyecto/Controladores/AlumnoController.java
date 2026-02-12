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

@RestController
@RequestMapping("/alumnos")
@Tag(name = "Alumnos", description = "Operaciones sobre alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository repo;

    // Devuelve todos los alumnos
    @GetMapping
    @Operation(summary = "Listar alumnos", description = "Devuelve la lista completa de alumnos")
    public List<Alumno> listar() {
        return repo.findAll();
    }

    // Devuelve alumno por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener alumno por id", description = "Devuelve un alumno por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno encontrado", content = @Content(schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public Optional<Alumno> getIdAlumno(@PathVariable Long id) {
        return repo.findById(id);
    }

    // Crea alumno
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear alumno", description = "Crea un nuevo alumno con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alumno creado", content = @Content(schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Alumno> crear(@Valid @RequestBody AlumnoDTO dto) {
        Alumno a = new Alumno();
        a.setNombre(dto.getNombre());
        a.setEmail(dto.getEmail());
        Alumno saved = repo.save(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Actualiza alumno
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar alumno", description = "Actualiza los datos de un alumno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno actualizado", content = @Content(schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @Valid @RequestBody AlumnoDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setNombre(dto.getNombre());
            existing.setEmail(dto.getEmail());
            Alumno saved = repo.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Elimina alumno
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar alumno", description = "Elimina un alumno por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
