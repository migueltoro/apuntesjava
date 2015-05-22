package us.lsi.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import us.lsi.geometria.*;



public class MarcoDeTrabajo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void muestra() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MarcoDeTrabajo frame = new MarcoDeTrabajo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args){		
		muestra();
//		System.out.println(transformX(xMin)+","+transformY(centroY)+","+transformX(xMax)+","+transformY(centroY));
	}
	/**
	 * Create the frame.
	 */
	public MarcoDeTrabajo() {
		super( "Drawing lines, rectangles and ovals" );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, anchura.intValue(), altura.intValue());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	public void paint(Graphics g ){
		super.paint(g); 
		Graphics2D g2  = (Graphics2D) g;
		g2.setTransform(af);	
		objetos.draw(g2);
		} 	
	
	public static Double anchura = 800.;
	public static Double altura = 800.;
	public static Double xMin;
	public static Double xMax;
	public static Double yMin;
	public static Double yMax;
	public static Double tamX;
	public static Double centroX;
	public static Double tamY;
	public static Double centroY;
	public static AffineTransform af = null;
	
	public static ObjetoGeometricoAgregado2D objetos = ObjetoGeometricoAgregado2D.create();
	
	public static void setMarco(double x1, double x2, double y1, double y2){
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		tamX = xMax-xMin;
		centroX = (xMax+xMin)/2;
		tamY = yMax-yMin;
		centroY = (yMax+yMin)/2;
		double ax = anchura/tamX;
		double ay = - altura/tamY;
		double bx = - xMin*anchura/tamX;
	    double by = yMax*altura/tamY;
	    System.out.println("Afin ="+ax+","+bx+","+ay+","+by);
		MarcoDeTrabajo.af = new AffineTransform(ax,0,0,ay,bx,by);
	}
	
	public static void fill(Graphics2D g, Poligono2D p){
		Polygon polygon = new Polygon();
		for(Punto2D pt: p.getVertices()){
			polygon.addPoint(pt.getX().intValue(), pt.getY().intValue());
		}
		g.fillPolygon(polygon);
	}

	public static void fill(Graphics2D g, Punto2D p){
		g.fillOval(p.getX().intValue()-4, p.getY().intValue()-4, 8, 8);
	}
	
	public static void draw(Graphics2D g, Punto2D p1, Punto2D p2){
		g.drawLine(p1.getX().intValue(), p1.getY().intValue(), p2.getX().intValue(), p2.getY().intValue());
	}
	
}
