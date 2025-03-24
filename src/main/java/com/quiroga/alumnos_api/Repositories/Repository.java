package com.quiroga.alumnos_api.Repositories;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

// Metodos que implementan AlumnosRepository y DireccionRepository, que siguen las operaciones
// CRUD (Create, Read, Update, Delete)
public interface Repository<T> {
    public void save(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    public void deleteByID (int id) throws SQLException;
    public Optional<T> findByID (int id) throws SQLException;
    public int count () throws SQLException;
    public List<T> findAll () throws SQLException;

}
