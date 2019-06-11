package ar.edu.um.programacion2.DAO;

import ar.edu.um.programacion2.clases.Persona;
import ar.edu.um.programacion2.clases.Producto;
import ar.edu.um.programacion2.clases.Venta;

public interface IVentaDAO {
	public void insertar(Venta pr);
	public void actualizar(Venta pr);
	public void borrar(Venta pr);
	public Venta find(Long id);

}
