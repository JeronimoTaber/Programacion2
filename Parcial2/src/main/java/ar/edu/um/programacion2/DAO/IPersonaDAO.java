package ar.edu.um.programacion2.DAO;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import ar.edu.um.programacion2.clases.Persona;

public interface IPersonaDAO {
	public void insertar(Persona p);
	public void actualizar(Persona p);
	public void borrar(Persona p);
	public Persona find(Long id);
	public BlockingQueue<Persona> findAll() throws InterruptedException;
	public List<Persona> findAllActivos();
    public List<Persona> findByNombreOrApellidoLike(String nombre);

}
