package Principal;
import java.awt.EventQueue;
//import java.awt.Panel;
import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import CargaDeImagenes.*;

public class VentanaPrincipal {
	private JFrame frame;
	private PanelImagen panel;
	private PanelImagen panel2;
	private JButton btnCargarImagenPrincipal;
	private JButton btnCargarImagenDesordenada;
	private JButton btnOrdenar;
	private JTextField numFilas;
	private JTextField numColumnas;
	private JLabel lblFilas;
	private JLabel lblColumnas;
	private JButton btnSalir;
	
	private Puzle original;
	private Puzle desordenado;
	private File carpetaOriginal;
	private File carpetaDesordenado;
	private File carpetaSucesores;
	private File carpetaMovimientos;
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal() {
		initialize();
	}

	private void initialize() {	
		carpetaOriginal=new File("original");
		carpetaDesordenado=new File("desordenado");
		carpetaMovimientos=new File("movimientos");
		carpetaSucesores=new File("sucesores");
		
		if(!carpetaOriginal.isFile()){
			carpetaOriginal.mkdir();
		}
		
		if(!carpetaDesordenado.isFile()){
			carpetaDesordenado.mkdir();
		}
		
		if(!carpetaMovimientos.isFile()){
			carpetaMovimientos.mkdir();
		}
		
		if(!carpetaMovimientos.isFile()){
			carpetaSucesores.mkdir();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 947, 616);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new PanelImagen();
		panel.setBounds(12, 135, 448, 287);
		frame.getContentPane().add(panel);
		
		panel2 = new PanelImagen();
		panel2.setBounds(484, 135, 433, 287);
		frame.getContentPane().add(panel2);
		
		btnCargarImagenPrincipal = new JButton("Cargar Imagen principal");
		btnCargarImagenPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String file="";
				JFileChooser dlg=new JFileChooser();
				int option=dlg.showOpenDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){
					file=dlg.getSelectedFile().getAbsolutePath();
					panel.setImagen(file);
					panel.setRuta(file);
				}
			}
		});
		
		btnCargarImagenPrincipal.setBounds(12, 74, 191, 25);
		frame.getContentPane().add(btnCargarImagenPrincipal);
		
		btnCargarImagenDesordenada = new JButton("Cargar imagen desordenada");
		btnCargarImagenDesordenada.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String file="";
				JFileChooser dlg=new JFileChooser();
				int option=dlg.showOpenDialog(null);
				if(option==JFileChooser.APPROVE_OPTION){
					file=dlg.getSelectedFile().getAbsolutePath();
					panel2.setImagen(file);
					panel2.setRuta(file);
				}
			}
		});
		btnCargarImagenDesordenada.setBounds(487, 74, 221, 25);
		frame.getContentPane().add(btnCargarImagenDesordenada);
		
		btnOrdenar = new JButton("Ordenar");
		
		btnOrdenar.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				File imagen1=new File(panel.getRuta());
				File imagen2=new File(panel2.getRuta());
				
				try {
					int filas=Integer.parseInt(numFilas.getText());
					int columnas=Integer.parseInt(numColumnas.getText());
					
					original=Operaciones.generarPuzle(imagen1,filas,columnas,"original");
					desordenado=Operaciones.generarPuzle(imagen2,filas,columnas,"desordenado");
					if(Operaciones.esIgual(original, desordenado)){
						Operaciones.encontrarHueco(desordenado);
		
						System.out.println("ESTADO INICIAL:");
						System.out.println();
						System.out.println(desordenado);
						
						System.out.println("SUCESORES DEL ESTADO ACTUAL:");
						System.out.println();
						ArrayList<Puzle> suc=Operaciones.sucesores(desordenado);
						
						for(int i=0;i<suc.size();i++){
							System.out.println("Sucersor "+i+":\n"+suc.get(i));
							Operaciones.unirPuzle(suc.get(i), "sucesores");
						}
						
						System.out.println("MOVIENDO HACIA ABAJO");
						System.out.println();
						desordenado.MoverAbajo();
						System.out.println(desordenado);
						panel2 = new PanelImagen();
						panel2.setBounds(484, 135, 433, 287);
						frame.getContentPane().add(panel2);
						panel2.setImagen(Operaciones.unirPuzle(desordenado, "movimientos"));
						btnOrdenar.setEnabled(false);
					}else{
						System.out.println("Error, la imagen no es la misma");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnOrdenar.setBounds(500, 486, 97, 25);
		frame.getContentPane().add(btnOrdenar);
		
		numFilas = new JTextField();
		numFilas.setText("4");
		numFilas.setBounds(333, 473, 116, 22);
		frame.getContentPane().add(numFilas);
		numFilas.setColumns(10);
		
		numColumnas = new JTextField();
		numColumnas.setText("4");
		numColumnas.setBounds(333, 508, 116, 22);
		frame.getContentPane().add(numColumnas);
		numColumnas.setColumns(10);
		
		lblFilas = new JLabel("Filas");
		lblFilas.setBounds(193, 474, 35, 16);
		frame.getContentPane().add(lblFilas);
		
		lblColumnas = new JLabel("Columnas");
		lblColumnas.setBounds(172, 511, 56, 16);
		frame.getContentPane().add(lblColumnas);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new BtnSalirActionListener());
		btnSalir.setBounds(639, 486, 97, 25);
		frame.getContentPane().add(btnSalir);
	}
	
	private class BtnSalirActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			File[] ficherosOriginal=carpetaOriginal.listFiles();
			File[] ficherosDesordenado=carpetaDesordenado.listFiles();
			
			for(int i=0;i<ficherosOriginal.length;i++)
				ficherosOriginal[i].delete();
			
			for(int i=0;i<ficherosDesordenado.length;i++)
				ficherosDesordenado[i].delete();
			
			carpetaOriginal.delete();
			carpetaDesordenado.delete();
			System.exit(0);		
		}
	}
}
