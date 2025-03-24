package com.quiroga.alumnos_api.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionSQLite {
    private static final DataSource dataSource;
    private static final String URL = "jdbc:sqlite:alumnos.db";

    static {
        var config = new HikariConfig();
        config.setJdbcUrl(URL);
        // Configuracion
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setLeakDetectionThreshold(5000);
        dataSource = new HikariDataSource(config);
    }
    public static Connection getConnection () throws SQLException {
        return dataSource.getConnection();
    }

}
