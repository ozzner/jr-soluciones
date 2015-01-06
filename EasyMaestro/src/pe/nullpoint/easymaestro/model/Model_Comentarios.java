package pe.nullpoint.easymaestro.model;

public class Model_Comentarios {

	private String mensaje;
	private String tipo;
	
	
	
	/**
	 * 
	 */
	public Model_Comentarios() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param mensaje 
	 * @param tipo 
	 */
	public Model_Comentarios(String mensaje, String tipo) {
		super();
		this.mensaje = mensaje;
		this.tipo = tipo;
	}
	
	
	
	
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	
	
	
	
	
}
