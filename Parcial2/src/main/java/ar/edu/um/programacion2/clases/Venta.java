package ar.edu.um.programacion2.clases;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import ar.edu.um.programacion2.DAO.ProductoDAO;

public class Venta {
	protected Long id;
	protected Date fechaVenta;
	protected Long valorVenta;
	protected String descripcion;
	protected List<Producto> productos;
	protected Long idPersona;

	
	public Venta(Long id,java.util.Date date, Long valorVenta, String descripcion, List<Producto> productos,Long idPersona) {
		super();
		this.id = id;
		this.fechaVenta = date;
		this.valorVenta = valorVenta;
		this.descripcion = descripcion;
		this.productos = productos;
		this.idPersona = idPersona;
	}
	public Venta(java.util.Date date, Long valorVenta, String descripcion, List<Producto> productos,Long idPersona) {
		super();
		this.fechaVenta = date;
		this.valorVenta = valorVenta;
		this.descripcion = descripcion;
		this.productos = productos;
		this.idPersona = idPersona;
	}
	public Venta() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public Long getValorVenta() {
		return valorVenta;
	}
	public void setValorVenta(Long valorVenta) {
		this.valorVenta = valorVenta;
	}
	public String getDescripción() {
		return descripcion;
	}
	public void setDescripción(String descripción) {
		this.descripcion = descripción;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public void setProductoId(Connection con) {
		ProductoDAO productodao = new ProductoDAO(con);
		for (int i = 0; i < productos.size(); i++) {
			productodao.insertarif(productos.get(i));
			//System.out.println("es: " + productos.get(i).getId());
		}
	}
	
}
