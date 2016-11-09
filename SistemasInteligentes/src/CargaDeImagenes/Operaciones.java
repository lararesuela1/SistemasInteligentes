package CargaDeImagenes;

import java.awt.Graphics2D;
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Problema.Estado;
//import javax.swing.ImageIcon;
//import javax.swing.SingleSelectionModel;

public class Operaciones {

	public static Puzle generarPuzle(File file, int filas, int columnas,String carpeta) throws IOException {
		
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);
        int chunks = filas * columnas;
        int chunkWidth = image.getWidth() / columnas; 
        int chunkHeight = image.getHeight() / filas;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; 
        
        for (int x = 0; x < filas; x++) {
            for (int y = 0; y < columnas; y++) {
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "jpg", new File(carpeta+"\\"+i + ".jpg"));
        }  
        
        Pieza[][] matriz=new Pieza[filas][columnas];
        
        int imagenActual=0;
        for(int i=0;i<filas;i++)
        	for(int j=0;j<columnas;j++){
        		
        		matriz[i][j]=new Pieza(carpeta+"\\"+imagenActual + ".jpg",imagenActual);
        		File f=new File(carpeta+"\\"+imagenActual + ".jpg");
        		f.setWritable(true);
        		f.setReadable(true);
        		imagenActual++;
        	}
        
        Puzle p=new Puzle(matriz);
        return p;
        
    }
	public static Estado generarEstadoInicial(Puzle p){
		
		int[][]matriz=new int[p.getP().length][p.getP()[0].length];
		for(int i=0;i<p.getP().length;i++)
			for(int j=0;i<p.getP()[0].length;j++)
				matriz[i][j]=p.getP()[i][j].getId();
		
		return new Estado(matriz,p.getFilaHueco(),p.getColumnaHueco());
		
	}
	public static boolean esIgualInicial (Puzle puzleOriginal, Puzle puzleDesordenado){
    	
    	boolean esigual=false,encontrado=false,error=false;
    	int aciertos=0;
    	
    	for(int i=0;i<puzleOriginal.getP().length&&!error;i++){	
			for(int j=0;j<puzleOriginal.getP()[0].length&&!error;j++){

				for(int k=0;k<puzleDesordenado.getP().length&&!encontrado;k++){
					for(int l=0;l<puzleDesordenado.getP()[0].length&&!encontrado;l++){
						
						if(!puzleDesordenado.getP()[k][l].getVisitado() && 
								comparar_Imagenes(puzleOriginal.getP()[i][j].getRuta(),puzleDesordenado.getP()[k][l].getRuta()) ){
							
							puzleDesordenado.getP()[k][l].setVisitado(true);	
							puzleDesordenado.getP()[k][l].setId(puzleOriginal.getP()[i][j].getId());
							aciertos ++;
							encontrado=true;
						}
					}	
				}
				if(!encontrado){
					error=true;
				}else{
					encontrado=false;
				}
			}
    	}
    	if (aciertos==puzleOriginal.getP().length*puzleDesordenado.getP()[0].length){
    		esigual=true;
    	}
    	return esigual;
    }  	
       
    
    
	public static boolean comparar_Imagenes(String ima1, String ima2)     {         
		boolean iguales=true;                   
		
		byte[] arreglo_byte1=obtener_Imagen_Byte(ima1);        
		byte[] arreglo_byte2=obtener_Imagen_Byte(ima2);                   
		
		if(arreglo_byte1!=null && arreglo_byte2!=null) {   
			
			//Compara si el tama�o de los arreglos es diferente             
			if(arreglo_byte1.length!=arreglo_byte2.length) {  
				
				//Si son diferentes los tama�os las imagenes tambien.                
				iguales=false;                         
			} else { 
				
				int i=0;
				while(i<arreglo_byte1.length && iguales==true){
					
					//Compara Byte a Byte la Imagen
					if(arreglo_byte1[i]!=arreglo_byte2[i]){
						
						iguales=false;
					
					}
				 i++;
				}
			}	
		}
		return iguales;
	}
				
	public static byte[] obtener_Imagen_Byte(String n){  
		
		String dir=System.getProperty("user.dir")+"\\"+n;         
		File file = new File(dir);        
		byte[] bytes=null;
		if(!file.exists()){          
			System.out.println ("La Imagen No Existe. Verifique El Nombre y la Ruta");             
				
		}else{
			
			FileInputStream fis = null;    
			
			try{     
				
				fis=new FileInputStream(file);   
				
			}catch (Exception ex){
				System.out.println ("Error "+ex);
			}  
			      				
			ByteArrayOutputStream bos = new ByteArrayOutputStream();         
			byte[] buf = new byte[1024];         
			try{  
	    	
				for (int readNum; (readNum = fis.read(buf)) != -1;){                 
					bos.write(buf, 0, readNum);                 
				}         
			}catch (IOException ex){ 
	       		
				System.out.println ("Error "+ex);         
			}        
	       
			bytes = bos.toByteArray();         
	   	     
		}
		return bytes;
	}


//MOVIMIENTOS	

public ArrayList<String> acciones(Estado e){
	ArrayList<String> movimientos = new ArrayList<String>();
	
	if (moverAbajo(e) == 0){
		movimientos.add("ABAJO");
		moverArriba(e);
		
	}
	if (moverArriba(e) == 0){
		movimientos.add("ARRIBA");
		moverAbajo(e);
	}
	
	if (moverDer(e) == 0){
		movimientos.add("DERECHA");
		moverIzq(e);
	}
	
	if (moverIzq(e) == 0){
		movimientos.add("IZQUIERDA");
		moverDer(e);
	}
	
	return movimientos;
}	

public int mover(Estado e,String movimiento){
	int movido=1;
	
	switch(movimiento){
		case "ARRIBA":
			movido=moverArriba(e);
			break;
		case "ABAJO":
			movido=moverAbajo(e);
			break;
		case "IZQUIERDA":
			movido=moverIzq(e);
			break;
		case "DERECHA":
			movido=moverDer(e);
			break;

	}
	return movido;
}

public int moverIzq(Estado e){
	int correcto=1;
	if(e.getColumnaHueco()>0){
		intercambia(e.getEstado(),e.getFilaHueco(),e.getColumnaHueco(),e.getFilaHueco(),e.getColumnaHueco()-1);
		e.setColumnaHueco(e.getColumnaHueco()-1);
		correcto=0;
	}
		
	return correcto;
}

public int moverDer(Estado e){
	int correcto=1;
	if(e.getColumnaHueco()<e.getEstado()[0].length-1){
		intercambia(e.getEstado(),e.getFilaHueco(),e.getColumnaHueco(),e.getFilaHueco(),e.getColumnaHueco()+1);
		e.setColumnaHueco(e.getColumnaHueco()+1);
		correcto=0;
	}
	
	return correcto;
}

public int moverArriba(Estado e){
	int correcto=1;
	if(e.getFilaHueco()>0){
		intercambia(e.getEstado(),e.getFilaHueco(),e.getColumnaHueco(),e.getFilaHueco()-1,e.getColumnaHueco());
		e.setFilaHueco(e.getFilaHueco()-1);
		correcto=0;
	}
	
	return correcto;
}

public int moverAbajo(Estado e){
	int correcto=1;
	if(e.getFilaHueco()<e.getEstado().length-1){
		intercambia(e.getEstado(),e.getFilaHueco(),e.getColumnaHueco(),e.getFilaHueco()+1,e.getColumnaHueco());
		e.setFilaHueco(e.getFilaHueco()+1);
		correcto=0;
	}
	
	return correcto;
}	
public void intercambia(int[][] p,int filaOrigen,int columnaOrigen,int filaDestino,int columnaDestino){
	int aux=p[filaOrigen][columnaOrigen];
	int destino=p[filaDestino][columnaDestino];
	p[filaOrigen][columnaOrigen]=destino;
	p[filaDestino][columnaDestino]=aux;
	
}
//MOVIMIENTOS
}
