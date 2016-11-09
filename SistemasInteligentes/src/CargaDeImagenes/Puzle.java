package CargaDeImagenes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//import java.util.Arrays;

public class Puzle implements Cloneable{

	private Pieza[][] p;
	private int filaHueco=0;
	private int columnaHueco=0;

	//CONSTRUCTORES
	public Puzle(Pieza[][]p){
		this.p=p;
		this.setHueco();
	}
		
	public Puzle(Pieza[][]p,int filaHueco,int columnaHueco){
		this.p=p;
		this.filaHueco=filaHueco;
		this.columnaHueco=columnaHueco;
	}
	//CONSTRUCTORES
	
	
	//GETTERS AND SETTERS
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
	
	public void setHueco(){
		
		for(int i=0;i<p.length;i++)
			for(int j=0;j<p[0].length;j++){
				
				if(p[i][j].getId()==0){
					setHueco(i, j);
				}
			}
	}
	
	public Pieza getHueco(){
		return p[filaHueco][columnaHueco];
	}
	
	//GETTERS AND SETTERS	
	
		

	
	
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

//MISC
public boolean isOrdenado(){
    	
    	boolean ordenado=false,error=false;
    	int idEsperado=0;
    	
    	for(int i=0;i<p.length&&!error;i++){	
			for(int j=0;j<p[0].length&&!error;j++){
				if(p[i][j].getId()!=idEsperado)
					error=true;
					idEsperado++;
			}
		
		}
    	
    	if (!error){
    		ordenado=true;
    	}
    	return ordenado;
    } 
	
	
	
	public String unir(String carpeta) throws IOException{
        // Ir a buscar los archivos de imagen
    	
    	int chunks=p.length*p[0].length;
    	File imgFiles [] = new File [chunks];
        int chunkActual=0;
        
        for(int i = 0; i <p.length; i ++)
        	for(int j=0;j<p[0].length;j++){
        		imgFiles [chunkActual] = new File (p[i][j].getRuta());
        		chunkActual++;
        	}
        
       // Crear una matriz de imagen bufferd de archivos de imagen
        BufferedImage buffImages[] = new BufferedImage [chunks];
		for (int i = 0; i <chunks; i ++) {
			buffImages [i] = ImageIO.read (imgFiles [i]);
        }
		
        int tipo = buffImages [0] .getType ();
        int chunkWidth = buffImages [0] .getWidth ();
        int chunkHeight = buffImages [0] .getHeight ();

        // Inicializaci�n de la imagen final
        BufferedImage finalImg = new BufferedImage (chunkWidth * p[0].length, chunkHeight * p.length, tipo);

        int num = 0;
        for (int i = 0; i <p.length; i ++) {
            for (int j=0; j<p[0].length; j ++) {
                Graphics2D fin = finalImg.createGraphics();
                fin.drawImage(buffImages [num], chunkWidth * j, chunkHeight * i, null);
                num ++;
            }
        }
        
        //System.out.println ( "Imagen concatenado .....");
        File conteoFicheros=new File(carpeta);
        String[] imagenes=conteoFicheros.list();
        ImageIO.write (finalImg, "jpg", new File(carpeta+"\\"+imagenes.length+".jpg"));
        
        return carpeta+"\\"+imagenes.length+".jpg";
    }
	
    public Puzle clone(){
        
        Pieza[][]pClon=new Pieza[p.length][p[0].length];
    	
        for(int i=0;i<p.length;i++)
    		for(int j=0;j<p[0].length;j++)
    			pClon[i][j]=p[i][j];
        
        
        return new Puzle(pClon,this.filaHueco,this.columnaHueco);
    }
    
    
	@Override
	public String toString() {
		String devolucion="";
		
		for(int i=0;i<p.length;i++){
			
			for(int j=0;j<p[0].length;j++){
				devolucion+=p[i][j]+"	";
				
			}
			devolucion+="\n";
		}
			
		return devolucion;		
	}
	//MISC

}
