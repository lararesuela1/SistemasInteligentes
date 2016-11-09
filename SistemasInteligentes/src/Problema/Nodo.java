package Problema;

public class Nodo implements Comparable<Nodo> {
	Estado e;
	int costo;
	String accion;
	int valor;
	
	public Nodo(Estado e, int costo, String accion, int valor) {
		super();
		this.e = e;
		this.costo = costo;
		this.accion = accion;
		this.valor = valor;
	}
	
	public Estado getE() {
		return e;
	}
	public void setE(Estado e) {
		this.e = e;
	}
	public int getCosto() {
		return costo;
	}
	public void setCosto(int costo) {
		this.costo = costo;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	public int compareTo(Nodo nodo) {
	      if (valor < nodo.valor) {
	          return -1;
	      }
	      if (valor > nodo.valor) {
	          return 1;
	            }
	            return 0;
	        }
}
