package com.quiroga.alumnos_api.Controller;

import com.quiroga.alumnos_api.Entities.AlumnosEntity;
import com.quiroga.alumnos_api.Entities.DireccionEntity;
import com.quiroga.alumnos_api.Exceptions.AlumnoNoEncontradoException;
import com.quiroga.alumnos_api.Exceptions.DireccionNoEncontradaException;
import com.quiroga.alumnos_api.Service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping ("/api/direcciones")
public class DireccionController {
    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<DireccionEntity>> findAll() throws SQLException {
        try {
            List<DireccionEntity> direcciones = direccionService.findAll();
            return ResponseEntity.ok(direcciones);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<DireccionEntity> getDireccionById(@PathVariable int id) {
        try {
            DireccionEntity direccion = direccionService.findByID(id);
            return ResponseEntity.ok(direccion);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (DireccionNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping
    public ResponseEntity<DireccionEntity> save(@RequestBody DireccionEntity direccion) {
        try {
            direccionService.save(direccion);
            return ResponseEntity.ok(direccion);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DireccionEntity> update (@PathVariable int id, @RequestBody DireccionEntity direccion) {
        try {
            direccionService.update(id, direccion);
            return ResponseEntity.ok(direccion);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try{
            direccionService.delete(id);
            return ResponseEntity.ok().build();
        } catch(SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
