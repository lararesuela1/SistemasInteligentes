package CargaDeImagenes;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
 
public class PanelImagen extends JPanel {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Image imagen;
	private String ruta;
    public PanelImagen() {
    }
 
    public PanelImagen(String nombreImagen) {
        
    	if (nombreImagen != null) {
            imagen = new ImageIcon(nombreImagen).getImage();
        }
    }
 
 
    public void setImagen(String nombreImagen) {
        
    	if (nombreImagen != null) {
            imagen = new ImageIcon(nombreImagen).getImage();
        } else {
            imagen = null;
        }
 
        repaint();
    }
    public void setRuta(String ruta){
    	
    	this.ruta=ruta;
    	
    }
    
    public String getRuta(){
    	return ruta;
    }
 
    public void setImagen(Image nuevaImagen) {
        imagen = nuevaImagen;
 
        repaint();
    }
    
    public Image getImagen(){
    	return this.imagen;
    }
    
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(),
                              this);
 
            setOpaque(false);
        } else {
            setOpaque(true);
        }
 
        super.paint(g);
    }
}

