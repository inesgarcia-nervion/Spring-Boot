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
    @ResponseStatus(HttpStatus.CREATED)         // Al crear el alumno, devuelve el código 201
    public Alumno crear(@RequestBody Alumno alumno) {
        return repo.save(alumno);
    }

    // Actualiza alumno
    @PutMapping("/{id}")
    public Optional<Alumno> actualizar(@PathVariable Long id, @RequestBody Alumno alumno) {
        return repo.findById(id).map(existing -> {
            existing.setNombre(alumno.getNombre());
            existing.setEmail(alumno.getEmail());
            return repo.save(existing);
        });
    }

    // Elimina alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {       // ResponseEntity para manejar respuestas HTTP
        if (!repo.existsById(id)) {     // Si no existe el alumno, devuelve 404
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);            // Si existe, lo elimina y devuelve 204
        return ResponseEntity.noContent().build();
    }
}
