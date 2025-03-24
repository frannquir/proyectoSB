package com.quiroga.alumnos_api.Controller;

import com.quiroga.alumnos_api.Entities.AlumnosEntity;
import com.quiroga.alumnos_api.Exceptions.AlumnoNoEncontradoException;
import com.quiroga.alumnos_api.Service.AlumnosService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/alumnos")

public class AlumnoController {
    @Autowired
    private AlumnosService alumnosService;

    // Response entity permite tener control sobre las respuestas
    @GetMapping
    public ResponseEntity<List<AlumnosEntity>> findAll() throws SQLException {
        try {
            List<AlumnosEntity> alumnos = alumnosService.findAll();
            return ResponseEntity.ok(alumnos); // Si el find all no lanzo SQL Exception, retorna la lista.
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Crea la Response Entity con Internal server error.
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnosEntity> getAlumnoById(@PathVariable int id) {
        try {
            AlumnosEntity alumno = alumnosService.findByID(id);
            return ResponseEntity.ok(alumno);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (AlumnoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<AlumnosEntity> save(@RequestBody AlumnosEntity alumno) { // Debo aclarar que estoy guardando un alumno
        try {
            alumnosService.save(alumno);
            return ResponseEntity.ok(alumno);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnosEntity> update (@PathVariable int id, @RequestBody AlumnosEntity alumno) {
        try {
            alumnosService.update(id, alumno);
            return ResponseEntity.ok(alumno);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try{
            alumnosService.delete(id);
            return ResponseEntity.ok().build();
      } catch(SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
