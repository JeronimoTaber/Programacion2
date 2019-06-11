package ar.edu.um.programacion2.Parcial2;

import java.sql.Connection;
import java.util.UUID;

import ar.edu.um.programacion2.DAO.PersonaDAO;
import ar.edu.um.programacion2.DAO.VentaDAO;
import ar.edu.um.programacion2.Excepcion.ExceptionActivo;
import ar.edu.um.programacion2.clases.Persona;
import ar.edu.um.programacion2.clases.Venta;

public class ProcessVenta {

	public void realizarventa(Venta venta, Connection con) {
		try {
			PersonaDAO personadao = new PersonaDAO(con);
			Persona persona = personadao.find(venta.getIdPersona());
			persona.check();
			VentaDAO ventadao = new VentaDAO(con);
			venta.setProductoId(con);
			ventadao.insertar(venta);
		} catch (ExceptionActivo e) {
			System.out.println(e.toString());
		}
	}
}