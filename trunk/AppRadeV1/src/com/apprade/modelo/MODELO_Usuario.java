package com.apprade.modelo;

import java.util.Date;

/**
 * @author Renzo
 *
 */


public class MODELO_Usuario {
	
	private int usuarioID;
	private String email;
	private char sexo;
	private String nombre;
	private Date fechaNacimiento;
	private String apMaterno;
	private String apPaterno;
	private int idRanking;
	private int rate;
	private String uid;
	private Date fechaRegistro;
	private String password;
	

	public MODELO_Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MODELO_Usuario(int usuarioID, String email, char sexo, String nombre,
			Date fechaNacimiento, String apMaterno, String apPaterno,
			int idRanking, int rate, String uid, Date fechaRegistro,
			String password) {
		
		super();
		this.usuarioID = usuarioID;
		this.email = email;
		this.sexo = sexo;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.apMaterno = apMaterno;
		this.apPaterno = apPaterno;
		this.idRanking = idRanking;
		this.rate = rate;
		this.uid = uid;
		this.fechaRegistro = fechaRegistro;
		this.password = password;
	}



	public int getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public int getIdRanking() {
		return idRanking;
	}

	public void setIdRanking(int idRanking) {
		this.idRanking = idRanking;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
