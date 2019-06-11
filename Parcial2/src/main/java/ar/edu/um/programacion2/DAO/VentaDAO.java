package ar.edu.um.programacion2.DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.um.programacion2.clases.Persona;
import ar.edu.um.programacion2.clases.Producto;
import ar.edu.um.programacion2.clases.Venta;

public class VentaDAO implements IVentaDAO {
	protected Connection con;

	public VentaDAO(Connection con) {
		super();
		this.con = con;
	}

	public void insertar(Venta venta) {
		List<Producto> producto = venta.getProductos();
		String sqlInsertar = "INSERT INTO Ventas VALUES (null, ?, ?, ?, ?);";
		String sqlGet = "SELECT LAST_INSERT_ID();";
		String sqlInsertarVP = "INSERT INTO VP VALUES (null, ?, ?);";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sqlInsertar);
			Date sDate = convertUtilToSql(venta.getFechaVenta());
			stmt.setDate(1, sDate);
			stmt.setLong(2, venta.getValorVenta());
			stmt.setLong(3, venta.getIdPersona());
			stmt.setString(4, venta.getDescripción());
			stmt.execute();
			stmt.close();
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sqlGet);
			if (rs.next()) {
				do {
					venta.setId(rs.getLong("LAST_INSERT_ID()"));
				} while (rs.next());
				for (int i = 0; i < venta.getProductos().size(); i++) {
					stmt = con.prepareStatement(sqlInsertarVP);
					System.out.println("Time in java.sql.Date is : " + sDate);
					stmt.setLong(1, venta.getId());
					stmt.setLong(2, producto.get(i).getId());
					stmt.execute();
					stmt.close();
				}
			}
			stmt2.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public void actualizar(Venta venta) {
		String sqlUpdate = "UPDATE Ventas SET fechaVenta = ?, valorVenta = ?,idPersona = ?, descripcion = ? WHERE idVentas = ? ;";
		String sqlInsertarVP = "INSERT INTO VP VALUES (null, ?, ?);";
		String sqlBorrarVP = "DELETE FROM VP WHERE idVenta = ?;";
		List<Producto> producto = venta.getProductos();
		try {
			PreparedStatement stmt = con.prepareStatement(sqlUpdate);
			Date sDate = convertUtilToSql(venta.getFechaVenta());
			//System.out.println("Time in java.sql.Date is : " + sDate);;
			stmt.setDate(1, sDate);
			stmt.setLong(2, venta.getValorVenta());
			stmt.setLong(3, venta.getIdPersona());
			stmt.setString(4, venta.getDescripción());
			stmt.setLong(5, venta.getId());
			stmt.execute();
			stmt.close();
			PreparedStatement stmt2 = con.prepareStatement(sqlBorrarVP);
			stmt2.setLong(1, venta.getId());
			stmt2.execute();
			stmt2.close();
			for (int i = 0; i < venta.getProductos().size(); i++) {
				PreparedStatement stmt3 = con.prepareStatement(sqlInsertarVP);
				stmt3.setLong(1, venta.getId());
				stmt3.setLong(2, producto.get(i).getId());
				stmt3.execute();
				stmt3.close();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void borrar(Venta venta) {
		String sqlDelete = "DELETE FROM Ventas WHERE idVentas = ? ;";
		String sqlDeleteVP = "DELETE FROM VP WHERE idVenta = ? ;";		
		try {
			PreparedStatement stmt1 = con.prepareStatement(sqlDeleteVP);
			stmt1.setLong(1, venta.getId());
			stmt1.execute();
			stmt1.close();
			PreparedStatement stmt = con.prepareStatement(sqlDelete);
			stmt.setLong(1, venta.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Venta find(Long id) {
		String sqlfindventa = "SELECT * FROM Ventas WHERE idVentas = ?;";
		String sqlfindproducto = "SELECT pr.idProductos, pr.nombre, pr.descripcion, pr.precio FROM Productos AS pr INNER JOIN VP ON VP.idProducto = pr.idProductos WHERE VP.idVenta = ?;";
		Venta venta = new Venta();
		try {
			PreparedStatement stmt = con.prepareStatement(sqlfindventa);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			//ResultSetMetaData rsmd = rs.getMetaData();
			//int columnsNumber = rsmd.getColumnCount();
			if (rs.next()) {
				do {
				venta.setId(rs.getLong("idVentas"));
				venta.setDescripción(rs.getString("descripcion"));
				venta.setFechaVenta(rs.getDate("fechaVenta"));
				//venta.setProductos(rs.getString("productos"));
				venta.setValorVenta(rs.getLong("valorVenta"));
				} while (rs.next());
			} else {
				//producto = new Producto(0L, "null", "null", 0L);
				return null;
			}
			
			stmt.close();
			rs.close();
			PreparedStatement stmt2 = con.prepareStatement(sqlfindproducto);
			stmt2.setLong(1, id);
			ResultSet rs2 = stmt2.executeQuery();
			List<Producto> productos = new ArrayList<Producto>();
			while (rs2.next()) {
				//System.out.println(rs2.getString("nombre"));
				Producto pr =new Producto(rs2.getLong("pr.idProductos"),rs2.getString("nombre"),rs2.getString("descripcion"), rs2.getLong("precio"));
				productos.add(pr);
			}
			stmt2.close();
			rs2.close();
			venta.setProductos(productos);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return venta;
	}

}
