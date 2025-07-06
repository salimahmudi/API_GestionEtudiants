package com.example.etudiantapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-info")
    public Map<String, Object> getDatabaseInfo() {
        Map<String, Object> info = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            info.put("url", conn.getMetaData().getURL());
            info.put("username", conn.getMetaData().getUserName());
            info.put("driverName", conn.getMetaData().getDriverName());

            // Compter les étudiants
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM etudiants");
                if (rs.next()) {
                    info.put("studentCount", rs.getInt("count"));
                }
            }

            // Lister les tables
            List<String> tables = new ArrayList<>();
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            info.put("tables", tables);

        } catch (Exception e) {
            info.put("error", e.getMessage());
        }

        return info;
    }
}
