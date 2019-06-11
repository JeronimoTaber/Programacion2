package ar.edu.um.programacion2.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ar.edu.um.programacion2.Conexion.Conexion;
import ar.edu.um.programacion2.clases.Persona;
import ar.edu.um.programacion2.clases.Producto;
import ar.edu.um.programacion2.clases.Venta;

public class PersonaDAO implements IPersonaDAO {
	protected Connection con;

	public PersonaDAO() {
		super();
		Conexion conexion = new Conexion();
		con = conexion.getCon();
	}

	public PersonaDAO(Connection con) {
		super();
		this.con = con;
	}

	public void insertar(Persona p) {
		String sqlInsertar = "INSERT INTO Persons VALUES (null,?, ?,?,?);";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlInsertar);
			stmt.setString(1, p.getNombre());
			stmt.setString(2, p.getApellido());
			stmt.setLong(3, p.getDocumento());
			stmt.setBoolean(4, p.getActivo());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void actualizar(Persona p) {
		String sqlUpdate = "UPDATE Persons SET nombre = ? , apellido = ? , documento = ? , activo = ? WHERE id = ? ;";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlUpdate);
			stmt.setString(1, p.getNombre());
			stmt.setString(2, p.getApellido());
			stmt.setLong(3, p.getDocumento());
			stmt.setBoolean(4, p.getActivo());
			stmt.setLong(5, p.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void borrar(Persona p) {
		String sqlDelete = "DELETE FROM Persons WHERE id = ? ;";
		String sqlDeleteventa = "DELETE FROM Ventas WHERE idPersona = ? ;";
		String sqlDeleteVP = "DELETE FROM VP WHERE idVenta = ? ;";		
		String sqlfindventas = "SELECT idVentas FROM Ventas WHERE idPersona = ? ;";		

		try {
			PreparedStatement stmt3 = con.prepareStatement(sqlfindventas);
			stmt3.setLong(1, p.getId());
			ResultSet rs = stmt3.executeQuery();
			if (rs.next()) {
				do {
					PreparedStatement stmt1 = con.prepareStatement(sqlDeleteVP);
					stmt1.setLong(1, rs.getLong("idVentas"));
					stmt1.execute();
					stmt1.close();
				} while (rs.next());
			}
			PreparedStatement stmt = con.prepareStatement(sqlDeleteventa);
			stmt.setLong(1, p.getId());
			stmt.execute();
			stmt.close();
			PreparedStatement stmt2 = con.prepareStatement(sqlDelete);
			stmt2.setLong(1, p.getId());
			stmt2.execute();
			stmt2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Persona find(Long id) {
		String sqlBuscar = "SELECT * FROM Persons WHERE id = ?;";
		String sqlBuscarVenta = "SELECT idVentas FROM Ventas WHERE idPersona = ?;";
		Persona persona = new Persona();
		List<Venta> ventas = new ArrayList<Venta>();
		VentaDAO ventadao = new VentaDAO(con);
		try {
			PreparedStatement stmt = con.prepareStatement(sqlBuscar);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				do {
				persona.setId(rs.getLong("id"));
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				persona.setDocumento(rs.getLong("documento"));
				persona.setActivo(rs.getBoolean("activo"));
				} while (rs.next());
			} else {
				return null;
			}
			stmt.close();
			rs.close();
			PreparedStatement stmt2 = con.prepareStatement(sqlBuscarVenta);
			stmt2.setLong(1, id);
			ResultSet rs2 = stmt2.executeQuery();
			while (rs2.next()) {
				ventas.add(ventadao.find(rs2.getLong("idVentas")));
			}
			stmt2.close();
			rs2.close();
			persona.setVentas(ventas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persona;

	}

	public BlockingQueue<Persona> findAll() throws InterruptedException {
		BlockingQueue<Persona> people = new LinkedBlockingQueue<Persona>();
		String sqlBuscar = "SELECT * FROM Persons;";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlBuscar);
			while (rs.next()) {
				Persona persona = new Persona(rs.getLong("id"), rs.getString("nombre"), rs.getString("apellido"),rs.getLong("documento"),rs.getBoolean("activo"));
				people.put(persona);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people;
	}
	public List<Persona> findAllActivos() {
		List<Persona> people = new ArrayList<Persona>();
		String sqlBuscar = "SELECT * FROM Persons WHERE activo = 1;";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlBuscar);
			while (rs.next()) {
				Persona persona = new Persona(rs.getLong("id"), rs.getString("nombre"), rs.getString("apellido"),rs.getLong("documento"),rs.getBoolean("activo"));
				people.add(persona);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people;
	}
	public List<Persona> findByNombreOrApellidoLike(String nombre) {
		List<Persona> people = new ArrayList<Persona>();
		String sqlBuscar = "SELECT * FROM Persons WHERE nombre = ? or apellido = ?;";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlBuscar);
			stmt.setString(1, nombre);
			stmt.setString(2, nombre);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Persona persona = new Persona(rs.getLong("id"), rs.getString("nombre"), rs.getString("apellido"),rs.getLong("documento"),rs.getBoolean("activo"));
				people.add(persona);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return people;
	}	
}
