package Problema;

import java.util.ArrayList;

import CargaDeImagenes.Pieza;

public class Estado {
	
	private int[][] estado;
	private int filaHueco;
	private int columnaHueco;
	
	public Estado(int[][] estado) {
		this.estado = estado;
	}
	
	public Estado(int[][]estado,int filaHueco,int columnaHueco){
		this.estado=estado;
		this.filaHueco=filaHueco;
		this.columnaHueco=columnaHueco;
	}

	public int[][] getEstado() {
		return estado;
	}


	public int getFilaHueco() {
		return filaHueco;
	}

	public void setFilaHueco(int filaHueco) {
		this.filaHueco = filaHueco;
	}

	public int getColumnaHueco() {
		return columnaHueco;
	}

	public void setColumnaHueco(int columnaHueco) {
		this.columnaHueco = columnaHueco;
	}

	public ArrayList<Estado> sucesores(){
		
		for(int i=0;i<acciones.size();i++)
			
	}
	
}