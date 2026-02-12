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

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository repo;

    // Devuelve todos los alumnos
    @GetMapping
    public List<Alumno> listar() {
        return repo.findAll();
    }

    // Devuelve alumno por id
    @GetMapping("/{id}")
    public Optional<Alumno> getIdAlumno(@PathVariable Long id) {
        return repo.findById(id);
    }

    // Crea alumno
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Alumno> crear(@Valid @RequestBody AlumnoDTO dto) {
        Alumno a = new Alumno();
        a.setNombre(dto.getNombre());
        a.setEmail(dto.getEmail());
        Alumno saved = repo.save(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Actualiza alumno
    @PutMapping("/{id}")
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
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
