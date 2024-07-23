package demo_jdbc.respositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import demo_jdbc.models.ConstructorResult;

public class ConstructorResultRepository {
    
    String jdbcUrl = "jdbc:postgresql://localhost:5432/formula1";
    String user = "postgres";
    String password = "adonis1011";

    public List<ConstructorResult> getResultByYear(int year) {
        List<ConstructorResult> results = new ArrayList<>();

        try {
            // Establecer la conexion
            Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
            
            // Ejecutar la consulta
            String sql = "SELECT\n"
                    + "    r.year,\n"
                    + "    c.name,\n"
                    + "    COUNT(CASE WHEN res.position = 1 THEN 1 END) AS wins,\n"
                    + "    SUM(res.points) AS total_points,\n"
                    + "    RANK() OVER (PARTITION BY r.year ORDER BY SUM(res.points) DESC) AS season_rank\n"
                    + "FROM\n"
                    + "    results res\n"
                    + "JOIN\n"
                    + "    races r ON res.race_id = r.race_id\n"
                    + "JOIN\n"
                    + "    constructors c ON res.constructor_id = c.constructor_id\n"
                    + "WHERE r.year = ?\n"
                    + "GROUP BY\n"
                    + "    r.year, c.constructor_id, c.name\n"
                    + "ORDER BY\n"
                    + "    r.year, season_rank;";
            
            // Crear una sentencia
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, year);
            
            ResultSet rs = statement.executeQuery();
            
            // Procesar los resultados
            while(rs.next()) {
                String constructorName = rs.getString("name");
                int wins = rs.getInt("wins");
                int totalPoints = rs.getInt("total_points");
                int seasonRank = rs.getInt("season_rank");

                ConstructorResult result = new ConstructorResult(constructorName, wins, totalPoints, seasonRank);
                results.add(result);
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return results;
    }
}

