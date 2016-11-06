package CargaDeImagenes;

public class Puzle implements Cloneable{
	private Pieza[][] p;
	private int filaHueco=0;
	private int columnaHueco=0;

	//CONSTRUCTOR
	public Puzle(Pieza[][]p){
		this.p=p;
	}
	
	public Puzle(Pieza[][]p,int filaHueco,int columnaHueco){
		this.p=p;
		this.filaHueco=filaHueco;
		this.columnaHueco=columnaHueco;
	}
	
	//MÉTODOS GET Y SET
	public Pieza[][] getP() {
		return p;
	}
	
	public void setP(Pieza[][] p) {
		this.p = p;
	}
	
	public void setHueco(int f,int c){
		this.filaHueco=f;
		this.columnaHueco=c;
	}
	
	public Pieza getHueco(){
		return p[filaHueco][columnaHueco];
	}
	
	//Método para intercambiar las filas y columnas origen con otras específicas(destino) pasadas como argumento
	public void intercambia(int filaOrigen,int columnaOrigen,int filaDestino,int columnaDestino){
		Pieza aux=p[filaOrigen][columnaOrigen];
		Pieza destino=p[filaDestino][columnaDestino];
		p[filaOrigen][columnaOrigen]=destino;
		p[filaDestino][columnaDestino]=aux;
		
	}
	
	//MÉTODO MOVER A LA IZQUIERDA EL HUECO
	public int MoverIzq(){
		int correcto=1;
		if(columnaHueco>0){
			intercambia(filaHueco,columnaHueco,filaHueco,columnaHueco-1);
			setHueco(filaHueco,columnaHueco-1);
			correcto=0;
		}
			
		return correcto;
	}
	
	//MÉTODO MOVER A LA DERECHA EL HUECO
	public int MoverDer(){
		int correcto=1;
		if(columnaHueco<p[0].length-1){
			intercambia(filaHueco,columnaHueco,filaHueco,columnaHueco+1);
			setHueco(filaHueco,columnaHueco+1);
			correcto=0;
		}
		
		return correcto;
	}
	
	//MÉTODO MOVER HACIA ARRIBA EL HUECO
	public int MoverArriba(){
		int correcto=1;
		if(filaHueco>0){
			intercambia(filaHueco,columnaHueco,filaHueco-1,columnaHueco);
			setHueco(filaHueco-1,columnaHueco);
			correcto=0;
		}
		
		return correcto;
	}
	
	//MÉTODO MOVER HACIA ABAJO EL HUECO
	public int MoverAbajo(){
		int correcto=1;
		if(filaHueco<p.length-1){
			intercambia(filaHueco,columnaHueco,filaHueco+1,columnaHueco);
			setHueco(filaHueco+1,columnaHueco);
			correcto=0;
		}
		
		return correcto;
	}
	
	@Override
	public String toString() {
		String devolucion="";
		
		for(int i=0;i<p.length;i++){
			
			for(int j=0;j<p[0].length;j++){
				devolucion+=p[i][j];
				
			}
			devolucion+="\n";
		}
			
		return devolucion;		
	}
	
    //Método que sirve para clonar un objeto de la clase Puzle
	public Puzle clone(){
        Pieza[][]pClon=new Pieza[p.length][p[0].length];
        for(int i=0;i<p.length;i++)
    		for(int j=0;j<p[0].length;j++)
    			pClon[i][j]=p[i][j];
        return new Puzle(pClon,this.filaHueco,this.columnaHueco);
    }
}
