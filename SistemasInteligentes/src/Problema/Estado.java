package Problema;

import java.util.ArrayList;

public class Estado<G,H> {
	private G estado;
	private ArrayList<H> acciones;
	
	
	public Estado(G estado, ArrayList<H> acciones) {
		
		this.estado = estado;
		this.acciones = acciones;
	}


	public ArrayList<Estado<G,H>> sucesores(){
		
		for(int i=0;i<acciones.size();i++)
			
	}
	
}