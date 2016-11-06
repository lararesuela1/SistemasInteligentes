package Problema;

public class Nodo<G,H> {
	
	Estado<G,H> e;
	int costo;
	H accion;
	int valor;
	
	public Nodo(Estado<G, H> e, int costo, H accion, int valor) {
		super();
		this.e = e;
		this.costo = costo;
		this.accion = accion;
		this.valor = valor;
	}
	
	public Estado<G, H> getE() {
		return e;
	}
	public void setE(Estado<G, H> e) {
		this.e = e;
	}
	public int getCosto() {
		return costo;
	}
	public void setCosto(int costo) {
		this.costo = costo;
	}
	public H getAccion() {
		return accion;
	}
	public void setAccion(H accion) {
		this.accion = accion;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	
}
