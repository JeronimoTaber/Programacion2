package ar.edu.um.programacion2.clases;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ar.edu.um.programacion2.Excepcion.ExceptionActivo;

public class Persona {
	protected Long id;
	protected String nombre;
	protected String apellido;
	protected List<Venta> ventas;
	protected Long documento;
	protected Boolean activo;
	public Persona() {}

	public Persona(Long id, String nombre, String apellido, Long documento, Boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
		this.activo = activo;
	}

	public Persona(String nombre, String apellido, Long documento, Boolean activo) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
		this.activo = activo;
	}

	public Persona(String nombre, String apellido, Long documento) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
		this.activo = true;
	}
	
	public Persona(Long id) {
		super();
		this.id = id;;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Long getDocumento() {
		return documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public String getDocumentoFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
		DecimalFormat df = (DecimalFormat)nf;
		String format = df.format(documento);
		System.out.println(format);
		return format;
	}

	public void check() throws ExceptionActivo {
		if(activo == false) {
			throw new ExceptionActivo("La persona no esta activada");
		}
	}
}
