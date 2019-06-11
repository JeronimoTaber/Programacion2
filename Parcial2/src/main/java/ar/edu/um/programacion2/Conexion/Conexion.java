package ar.edu.um.programacion2.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	protected Connection con;

	public Conexion() {

		String usuario = "root";
		String password = "tango1995";

		// URL de conexion
		String url = "jdbc:mysql://localhost:3306/javatest?autoReconnect=true&useSSL=false&user=" + usuario + "&password=" + password;
		// Registro de MYsql en el driver manager
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		con = null;
		try {
			con = DriverManager.getConnection(url);
			if (con != null) {
				System.out.println("Conectado");
			}
		} catch (SQLException e) {
			System.out.println("No se pudo conectar a la base de datos");
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}
}