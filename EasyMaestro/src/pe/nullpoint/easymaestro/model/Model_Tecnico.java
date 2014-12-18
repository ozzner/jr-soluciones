package pe.nullpoint.easymaestro.model;

public class Model_Tecnico {
	
	private int tecnicoID;
	private String nombre;
	private String profesion;
	private String dni;
	private String direccion;
	private int  celular;
	private double lat;
	private double lon;
	private String calificacion;
	private String comentario;
	
	
	public Model_Tecnico() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Model_Tecnico(int tecnicoID, String nombre, String profesion,
			String dni, String direccion, int celular, double lat, double lon,
			String calificacion, String comentario) {
		super();
		this.tecnicoID = tecnicoID;
		this.nombre = nombre;
		this.profesion = profesion;
		this.dni = dni;
		this.direccion = direccion;
		this.celular = celular;
		this.lat = lat;
		this.lon = lon;
		this.calificacion = calificacion;
		this.comentario = comentario;
	}
	
	
	public int getTecnicoID() {
		return tecnicoID;
	}
	public void setTecnicoID(int tecnicoID) {
		this.tecnicoID = tecnicoID;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getCelular() {
		return celular;
	}
	public void setCelular(int celular) {
		this.celular = celular;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	

}
