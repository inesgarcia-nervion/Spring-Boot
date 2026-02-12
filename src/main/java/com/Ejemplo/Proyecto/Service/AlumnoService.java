package com.Ejemplo.Proyecto.Service;

import com.Ejemplo.Proyecto.Entidades.Alumno;
import com.Ejemplo.Proyecto.Repositorios.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository repository;

    public List<Alumno> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<Alumno> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Alumno crear(Alumno alumno) {
        return repository.save(alumno);
    }

    public Optional<Alumno> actualizar(Long id, Alumno alumnoActualizado) {
        return repository.findById(id).map(existente -> {
            existente.setNombre(alumnoActualizado.getNombre());
            existente.setEmail(alumnoActualizado.getEmail());
            return repository.save(existente);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Alumno> buscarPorNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
}