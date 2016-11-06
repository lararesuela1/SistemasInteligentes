package CargaDeImagenes;

//import java.awt.Image;

public class Pieza {
	
	//private Image imagen;
	private String ruta;
	private boolean visitado;
	private int id;
	public Pieza(String ruta,int id) {
		super();
		//this.imagen = imagen;
		this.ruta = ruta;
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public boolean getVisitado() {
		return visitado;
	}
	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}
	@Override
	public String toString() {
		return ""+id;
	}
	
}
