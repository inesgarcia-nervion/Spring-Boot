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
import com.Ejemplo.Proyecto.Service.AlumnoService;
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
    private AlumnoService service; // Cambiar de repo a service

    @GetMapping
    @Operation(summary = "Listar alumnos", description = "Devuelve la lista completa de alumnos registrados en el sistema")
    public List<Alumno> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener alumno por ID")
    public ResponseEntity<Alumno> getIdAlumno(@PathVariable Long id) {
        return service.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear alumno")
    public ResponseEntity<Alumno> crear(@Valid @RequestBody AlumnoDTO dto) {
        Alumno a = new Alumno();
        a.setNombre(dto.getNombre());
        a.setEmail(dto.getEmail());
        Alumno saved = service.crear(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar alumno")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @Valid @RequestBody AlumnoDTO dto) {
        Alumno actualizado = new Alumno();
        actualizado.setNombre(dto.getNombre());
        actualizado.setEmail(dto.getEmail());
        
        return service.actualizar(id, actualizado)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar alumno")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}