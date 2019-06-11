package ar.edu.um.programacion2.DAO;

import java.util.concurrent.BlockingQueue;
import ar.edu.um.programacion2.clases.Producto;

public interface IProductoDAO {

		public void insertar(Producto pr);
		public Long insertarif(Producto pr);
		public void actualizar(Producto pr);
		public void borrar(Producto pr);
		public Producto find(String nombre);
		public BlockingQueue<Producto> findAll() throws InterruptedException;

}
