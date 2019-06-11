package ar.edu.um.programacion2.Parcial2;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import ar.edu.um.programacion2.Conexion.Conexion;
import ar.edu.um.programacion2.DAO.PersonaDAO;
import ar.edu.um.programacion2.DAO.ProductoDAO;
import ar.edu.um.programacion2.DAO.VentaDAO;
import ar.edu.um.programacion2.clases.Persona;
import ar.edu.um.programacion2.clases.Producto;
import ar.edu.um.programacion2.clases.Venta;

public class App {
	public static void main(String[] args) throws SQLException, InterruptedException {
		Conexion conexion = new Conexion();
		Connection con = conexion.getCon();
		PersonaDAO personadao = new PersonaDAO();
		//FIND_ALL
		System.out.println("FIND ALL");
		BlockingQueue<Persona> people = personadao.findAll();
		Persona persona = null;
		while(!people.isEmpty()) {
			persona = people.take();
			System.out.println(persona.getId() + ", " + persona.getNombre() + ", " + persona.getApellido()+  ", " + persona.getDocumento()+ ", " + persona.getActivo());
		}
		//FIND_ALL activos
		System.out.println("FIND ALL ACTIVOS");
		List<Persona> peoples = personadao.findAllActivos();
		Persona persona2 = null;
		for(int i=0;i<peoples.size();i++) {
			persona2 = peoples.get(i);
			System.out.println(persona2.getId() + ", " + persona2.getNombre() + ", " + persona2.getApellido()+  ", " + persona2.getDocumento()+ ", " + persona2.getActivo());
		}
		//findByNombreOrApellidoLike
		System.out.println("findByNombreOrApellidoLike");
		List<Persona> peoples2 = personadao.findByNombreOrApellidoLike("jeronimo");
		Persona persona3 = null;
		for(int i=0;i<peoples2.size();i++) {
			persona3 = peoples2.get(i);
			System.out.println(persona3.getId() + ", " + persona3.getNombre() + ", " + persona3.getApellido()+  ", " + persona3.getDocumento()+ ", " + persona3.getActivo());
		}
		//FIND
		System.out.println("FIND");
		Long id = 8L;
		Persona personafind = personadao.find(id);
		if(personafind != null) {
		System.out.println(personafind.getId() + ", " + personafind.getNombre() + ", " + personafind.getApellido()+  ", " + personafind.getDocumentoFormat()+ ", " + personafind.getActivo());
		}
		else {
			System.out.println("no se encontro ninguna persona");
		}
		//INSERT
		System.out.println("INSERT");
		Persona p = new Persona("jeronimo","taber",9340365L);
		//personadao.insertar(p);
		//UPDATE
		System.out.println("UPDATE");
		Persona u = new Persona(1L,"jerrr","taberr",37380966L,false);
		personadao.actualizar(u);
		//DELETE
		System.out.println("DELETE");
		Persona d = new Persona(1L);
		//personadao.borrar(d);
		
		System.out.println("Productos");
		ProductoDAO productodao = new ProductoDAO(con);
		// Producto_INSERT
		System.out.println("PRODUCTO_INSERT");
		 Producto pr = new Producto("cocaina","estupefaciente",400L);
		 //productodao.insertar(pr);
		//FIND
		System.out.println("FIND");
		Producto productofind = productodao.find("coca");
		if(productofind != null) {
		System.out.println(productofind.getId() + ", " + productofind.getNombre() + ", " + productofind.getDescripcion() + "," + productofind.getPrecio());
		}else {
			System.out.println("NO se encontro el producto");
		}
		//FIND_ALL
		System.out.println("FIND ALL");
		BlockingQueue<Producto> productosa = productodao.findAll();
		while(!productosa.isEmpty()) {
			Producto producto = productosa.take();
			System.out.println(producto.getId() + ", " + producto.getNombre() + ", " + producto.getDescripcion() + ", " + producto.getPrecio());
		}		System.out.println("FIND ALL");

		System.out.println("UPDATE");
		Producto up = new Producto(1L,"manis","comidas",25L);
		productodao.actualizar(up);
		//DELETE
		System.out.println("DELETE");
		Producto dr = new Producto(1L);
		//productodao.borrar(dr);
		
		System.out.println("Crear venta");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		dateFormat.format(date);
		//Persona persona = personadao.find(4L);
		List<Producto> productos = new ArrayList<Producto>();
		Producto pr1 =new Producto("pepsi", "pasteleria", 25L);
		Producto pr2 = new Producto("coca", "bebida", 30L);
		Producto pr3 = new Producto("lais", "chatarra", 60L);
		Producto pr4 = new Producto("nsa", "chatarra", 60L);
		Producto pr5 = new Producto("ger", "chatarra", 60L);
		productos.add(pr1);
		productos.add(pr2);
		productos.add(pr3);
		productos.add(pr4);
		productos.add(pr5);
		Venta venta = new Venta(date,115L,"venta",productos,3L);
		ProcessVenta proc = new ProcessVenta();
		proc.realizarventa(venta, con);
		
		//Borrar Venta
		System.out.println("Borrar Venta");
		//ventadao.borrar(venta);
		//Venta FIND
		System.out.println("Finde venta");
		VentaDAO ventadao = new VentaDAO(con);
		Venta ventafind = ventadao.find(7L);
		if(ventafind != null) {
			System.out.println("Venta");
			System.out.println(ventafind.getId()+","+ventafind.getValorVenta()+","+ventafind.getDescripción()+","+ventafind.getFechaVenta());
			System.out.println("Productos");
			for(int i=0;i<ventafind.getProductos().size();i++) {
				System.out.println(ventafind.getProductos().get(i).getNombre()+","+ventafind.getProductos().get(i).getPrecio());
			}
		}
		else {
			System.out.println("NO se enconto ninguna venta con ese id");
		}
		//Venta Update
		System.out.println("Venta update");
		Venta venta2 = new Venta(7L,date,120L,"venta",productos,4L);
		venta2.setProductoId(con);
		//ventadao.actualizar(venta2);

		//protected List<Producto> productos;
		//TEST PERSONA FIND
		Long idtest = 4L;
		Persona personafindtest = personadao.find(idtest);
		if(personafindtest != null) {
		System.out.println(personafindtest.getId() + ", " + personafindtest.getNombre() + ", " + personafindtest.getApellido());
		for(int i=0;i<personafind.getVentas().size();i++) {
            System.out.println(personafindtest.getVentas().get(i).getValorVenta()+","+personafindtest.getVentas().get(i).getDescripción()+","+personafindtest.getVentas().get(i).getFechaVenta());
        }
		}else {
			System.out.println("NO se enconto ninguna persona con ese id");
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}