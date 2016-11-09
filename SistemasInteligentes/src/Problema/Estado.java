package Problema;
import CargaDeImagenes.Operaciones;
import java.util.ArrayList;

import CargaDeImagenes.Pieza;
import CargaDeImagenes.Puzle;

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
	
	public Estado clone(){
        int[][]eClon=new int[estado.length][estado[0].length];
        for(int i=0;i<estado.length;i++)
    		for(int j=0;j<estado[0].length;j++)
    			eClon[i][j]=estado[i][j];
        return new Estado(eClon,this.filaHueco,this.columnaHueco);
    }
	
	public ArrayList<Estado> sucesores(){
		ArrayList<String> acciones=Operaciones.acciones(this);
		ArrayList<Estado> sucesores=new ArrayList<Estado>();
		for(int i=0;i<acciones.size();i++){
			sucesores.add(this.clone());
			Operaciones.mover(sucesores.get(sucesores.size()-1), acciones.get(i));
		}
		return sucesores;	
	}
}