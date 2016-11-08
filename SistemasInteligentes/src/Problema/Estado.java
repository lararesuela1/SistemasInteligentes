package Problema;

import java.util.ArrayList;

public class Estado {
	private int[][] estado;
	private String accionAnterior;
	
	
	public Estado(int[][] estado) {
		
		this.estado = estado;
		this.accionAnterior = "NADA";
	}
	
	public Estado(int[][] estado,String accionAnterior) {
		
		this.estado = estado;
		this.accionAnterior = accionAnterior;
	}

	public int[][] getEstado() {
		return estado;
	}

	public void setEstado(int[][] estado) {
		this.estado = estado;
	}

	public String getAccionAnterior() {
		return accionAnterior;
	}

	public void setAccionAnterior(String accionAnterior) {
		this.accionAnterior = accionAnterior;
	}

	public ArrayList<Estado> sucesores(){
		
		for(int i=0;i<acciones.size();i++)
			
	}
	
}