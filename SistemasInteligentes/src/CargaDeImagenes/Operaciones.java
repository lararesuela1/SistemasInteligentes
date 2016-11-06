package CargaDeImagenes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Operaciones {
	//Metodo para generar un Puzle dado un archivo "file", filas, columnas y una carpeta donde se guardarán los trozos del Puzle
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
	
	//Método que sirve encontrar el hueco en el Puzle Desordenado
	public static void encontrarHueco(Puzle puzleDesordenado){
		for(int i=0;i<puzleDesordenado.getP().length;i++)
			for(int j=0;j<puzleDesordenado.getP()[0].length;j++){
				if(puzleDesordenado.getP()[i][j].getId()==0){
					puzleDesordenado.setHueco(i, j);
				}
			}
	}
	
    //Método para unir los trozos del Puzle dado un Puzle y una carpeta que nos devuelve la ruta de la imagen final unida
	public static String unirPuzle(Puzle p,String carpeta) throws IOException{
        // Ir a buscar los archivos de imagen
    	int chunks=p.getP().length*p.getP()[0].length;
    	File imgFiles [] = new File [chunks];
        int chunkActual=0;
        
        for(int i = 0; i <p.getP().length; i ++)
        	for(int j=0;j<p.getP()[0].length;j++){
        		imgFiles [chunkActual] = new File ((p.getP()[i][j]).getRuta());
        		chunkActual++;
        	}
       // Crear una matriz de imagen buffered de archivos de imagen
        BufferedImage buffImages[] = new BufferedImage [chunks];
		
        for (int i = 0; i <chunks; i ++) {
			buffImages [i] = ImageIO.read (imgFiles [i]);
        }
        int tipo = buffImages [0] .getType ();
        int chunkWidth = buffImages [0] .getWidth ();
        int chunkHeight = buffImages [0] .getHeight ();
        
        // Inicialización de la imagen final
        BufferedImage finalImg = new BufferedImage (chunkWidth * p.getP()[0].length, chunkHeight * p.getP().length, tipo);
        int num = 0;
        for (int i = 0; i <p.getP().length; i ++) {
            for (int j=0; j<p.getP()[0].length; j ++) {
                Graphics2D fin = finalImg.createGraphics();
                fin.drawImage(buffImages [num], chunkWidth * j, chunkHeight * i, null);
                num ++;
            }
        }
        File conteoFicheros=new File(carpeta);
        String[] imagenes=conteoFicheros.list();
        ImageIO.write (finalImg, "jpg", new File(carpeta+"\\"+imagenes.length+".jpg"));
        
        return carpeta+"\\"+imagenes.length+".jpg";
    }
    
    //Método para comparar el Puzle original y el desordenado de manera que una vez visitado una porción del Puzle no vuelve a pasar
	//por esa ubicación en caso de que la imagen que compone el Puzle sea negra
    public static boolean esIgual (Puzle puzleOriginal, Puzle puzleDesordenado){
    	boolean esigual=false;
    	int aciertos =0;
    	for(int i=0;i<puzleOriginal.getP().length;i++){
			for(int j=0;j<puzleOriginal.getP().length;j++){
				for(int k=0;k<puzleDesordenado.getP().length;k++){
					for(int l=0;l<puzleDesordenado.getP().length;l++){
		if(comparar_Imagenes(puzleOriginal.getP()[i][j].getRuta(),puzleDesordenado.getP()[k][l].getRuta())& puzleDesordenado.getP()[k][l].getVisitado()==false){
				puzleDesordenado.getP()[k][l].setVisitado(true);	
				puzleDesordenado.getP()[k][l].setId(puzleOriginal.getP()[i][j].getId());
				aciertos ++;
		}
					}	
				}
			}
    	}
    	if (aciertos==puzleOriginal.getP().length*puzleDesordenado.getP()[0].length){
    		esigual=true;
    	}
    	return esigual;
  }
  
    //Método para comparar dos imágenes y ver si son iguales o no a nivel de byte
	public static boolean comparar_Imagenes(String ima1, String ima2)     {         
		boolean iguales=true;                   
		
		byte[] arreglo_byte1=obtener_Imagen_Byte(ima1);        
		byte[] arreglo_byte2=obtener_Imagen_Byte(ima2);                   
		
		if(arreglo_byte1!=null && arreglo_byte2!=null) {   
			//Compara si el tamaño de los arreglos es diferente             
			if(arreglo_byte1.length!=arreglo_byte2.length) {  
				//Si son diferentes los tamaños las imágenes también.                
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
	
	//Método que sirve para obtener el número de bytes de los que consta una imagen. Se llamará en el método comparar_Imagenes para así deducir si sus
	//tamaños son iguales al igual que el número de bytes
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
	
	//Método que devuelve los sucesores de un Puzle en caso de que el/los movimientos seán validos
	public static ArrayList<Puzle> sucesores (Puzle p){
    	ArrayList<Puzle> suc = new ArrayList<Puzle>();
    	
		if (p.MoverAbajo() == 0){
			Puzle ab=(Puzle) p.clone();//la instrucción p.clone nos sirve para tener siempre una copia del Puzle padre y que no se modifique el Puzle actual en cada movimiento
			suc.add(ab);
			p.MoverArriba();
			
		}
		if (p.MoverArriba() == 0){
			Puzle ar=(Puzle) p.clone();
			suc.add(ar);
			p.MoverAbajo();
		}
		
		if (p.MoverDer() == 0){
			Puzle der=(Puzle) p.clone();
			suc.add(der);
			p.MoverIzq();
		}
		
		if (p.MoverIzq() == 0){
			Puzle izq=(Puzle) p.clone();
			suc.add(izq);
			p.MoverDer();
		}
		
		return suc;
   }
}

