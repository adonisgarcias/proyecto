package demo_jdbc.respositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import demo_jdbc.models.DriverMaxPoint;

public class QueryRepository {
	
	String jdbcUrl = "jdbc:postgresql://localhost:5432/formula1";
	String user = "postgres";
	String password = "adonis1011";
	
	public List<DriverMaxPoint> getDriversMaxPoints() {
		
		ArrayList<DriverMaxPoint> results = new ArrayList<DriverMaxPoint>();
		
		String sql = "SELECT \n"
				+ "    d.forename || ' ' || d.surname AS driver_name,\n"
				+ "    SUM(r.points) AS total_points\n"
				+ "FROM \n"
				+ "    results r\n"
				+ "JOIN \n"
				+ "    drivers d ON r.driver_id = d.driver_id\n"
				+ "GROUP BY \n"
				+ "    d.forename, d.surname\n"
				+ "ORDER BY \n"
				+ "    total_points DESC\n"
				+ "LIMIT 10;";
		
		try {
			// Establecer la conexion
			Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
			
			
			// Crear una sentencia
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
			// Procesar los resultados
			while(rs.next()) {
				String driverName = rs.getString("driver_name");
				float points = rs.getFloat("total_points");
				
				DriverMaxPoint result = new DriverMaxPoint(driverName, points);
				results.add(result);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
	}
	
	public List<DriverMaxPoint> getConstructorsMaxPoints() {
		
		ArrayList<DriverMaxPoint> results = new ArrayList<DriverMaxPoint>();
		
		String sql = "SELECT d.name,\n"
				+ "SUM(r.points) AS total_points\n"
				+ "FROM results as r\n"
				+ "JOIN constructors as d ON r.constructor_id = d.constructor_id\n"
				+ "GROUP BY d.name\n"
				+ "ORDER BY total_points DESC\n"
				+ "LIMIT 10;";
		
		try {
			// Establecer la conexion
			Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
			
			
			// Crear una sentencia
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
			// Procesar los resultados
			while(rs.next()) {
				String driverName = rs.getString("name");
				float points = rs.getFloat("total_points");
				
				DriverMaxPoint result = new DriverMaxPoint(driverName, points);
				results.add(result);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
		
				
			
	}

}