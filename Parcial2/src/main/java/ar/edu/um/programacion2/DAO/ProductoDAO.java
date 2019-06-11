package ar.edu.um.programacion2.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ar.edu.um.programacion2.Conexion.Conexion;
import ar.edu.um.programacion2.clases.Producto;

public class ProductoDAO implements IProductoDAO {
	//protected Producto producto;
	protected Connection con;

	/*public ProductoDAO(Producto producto) {
		super();
		this.producto = producto;
		Conexion conexion = new Conexion();
		con = conexion.getCon();
	}*/

	public ProductoDAO() {
		super();
		Conexion conexion = new Conexion();
		con = conexion.getCon();
	}

	public ProductoDAO(Connection con) {
		super();
		this.con = con;
	}


	public void insertar(Producto pr) {
		String sqlInsertar = "INSERT INTO Productos VALUES (null, ?, ?, ?);";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlInsertar);
			stmt.setString(1, pr.getNombre());
			stmt.setString(2, pr.getDescripcion());
			stmt.setLong(3, pr.getPrecio());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Long insertarif(Producto pr) {
		String sqlBuscar = "SELECT * FROM Productos WHERE nombre = ?;";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlBuscar);
			stmt.setString(1, pr.getNombre());
			ResultSet rs = null;
			rs = stmt.executeQuery();
			if (rs.next()) {
				do {
					pr.setId(rs.getLong("idProductos"));
				} while (rs.next());
			} else {
				String sqlInsertar = "INSERT INTO Productos VALUES (null, ?, ?, ?);";
				String sqlGet = "SELECT LAST_INSERT_ID();";
				PreparedStatement stmt2 = con.prepareStatement(sqlInsertar);
				stmt2.setString(1, pr.getNombre());
				stmt2.setString(2, pr.getDescripcion());
				stmt2.setLong(3, pr.getPrecio());
				stmt2.execute();
				stmt2.close();
				Statement stmt3 = con.createStatement();
				ResultSet rs1 = stmt3.executeQuery(sqlGet);
				if (rs1.next()) {
					do {
						pr.setId(rs1.getLong("LAST_INSERT_ID()"));
					} while (rs1.next());
				}
				//System.out.println(producto.getNombre() + ", " + producto.getPrecio());
				stmt3.close();
				rs1.close();
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pr.getId();
	}

	public void actualizar(Producto pr) {
		String sqlUpdate = "UPDATE Productos SET nombre = ? , descripcion = ?, precio = ? WHERE idProductos = ? ;";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlUpdate);
			stmt.setString(1, pr.getNombre());
			stmt.setString(2, pr.getDescripcion());
			stmt.setLong(3, pr.getPrecio());
			stmt.setLong(4, pr.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void borrar(Producto pr) {
		String sqlDelete = "DELETE FROM Productos WHERE idProductos = ? AND idProductos NOT IN (select VP.idProducto from VP);";
		try {
			PreparedStatement stmt = con.prepareStatement(sqlDelete);
			stmt.setLong(1, pr.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Producto find(String nombre) {
		String sqlBuscar = "SELECT * FROM Productos WHERE nombre = ?;";
		Producto producto = null;
		try {
			PreparedStatement stmt = con.prepareStatement(sqlBuscar);
			stmt.setString(1, nombre);
			ResultSet rs = null;
			rs = stmt.executeQuery();
			if (rs.next()) {
				do {
					producto = new Producto(rs.getLong("idProductos"), rs.getString("nombre"),
					rs.getString("descripcion"), rs.getLong("precio"));
				} while (rs.next());
			} else {
				//producto = new Producto(0L, "null", "null", 0L);
				return null;
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return producto;
	}
	public BlockingQueue<Producto> findAll() throws InterruptedException {
		BlockingQueue<Producto> productos = new LinkedBlockingQueue<Producto>();
		String sqlBuscar = "SELECT * FROM Productos;";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlBuscar);
			while (rs.next()) {
				Producto producto = new Producto(rs.getLong("idProductos"),rs.getString("nombre"),rs.getString("descripcion"),rs.getLong("precio"));
				productos.put(producto);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}

}
