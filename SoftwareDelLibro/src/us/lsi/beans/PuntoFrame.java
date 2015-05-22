package us.lsi.beans;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Converter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PuntoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private ListenableRunnablePunto p1;
	private JLabel xValue;
	private JLabel yValue;
	private JLabel dValue;
	
	private Converter<Double,String> converter = new Converter<Double,String>(){

		@Override
		public String convertForward(Double a) {
			// TODO Auto-generated method stub
			return a.toString();
		}

		@Override
		public Double convertReverse(String a) {
			// TODO Auto-generated method stub
			return new Double(a);
		}};
		
	@SuppressWarnings("unused")
	private Converter<String,Double> converter2 = new Converter<String,Double>(){

		@Override
		public Double convertForward(String a) {
			// TODO Auto-generated method stub
			return new Double(a);
		}

		@Override
		public String convertReverse(Double a) {
			// TODO Auto-generated method stub
			return a.toString();
		}};
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PuntoFrame frame = new PuntoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PuntoFrame() {
		setDefaultValues();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JButton x = new JButton("x");
		GridBagConstraints gbc_x = new GridBagConstraints();
		gbc_x.insets = new Insets(0, 0, 5, 5);
		gbc_x.gridx = 0;
		gbc_x.gridy = 0;
		contentPane.add(x, gbc_x);
		
		xValue = new JLabel("xValue");
		GridBagConstraints gbc_xValue = new GridBagConstraints();
		gbc_xValue.insets = new Insets(0, 0, 5, 5);
		gbc_xValue.gridx = 1;
		gbc_xValue.gridy = 0;
		contentPane.add(xValue, gbc_xValue);
		
		JButton Fin = new JButton("Fin");
		Fin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				p1.stop();
			}
		});
		GridBagConstraints gbc_Fin = new GridBagConstraints();
		gbc_Fin.insets = new Insets(0, 0, 5, 0);
		gbc_Fin.gridx = 6;
		gbc_Fin.gridy = 0;
		contentPane.add(Fin, gbc_Fin);
		
		JButton y = new JButton("y");
		GridBagConstraints gbc_y = new GridBagConstraints();
		gbc_y.insets = new Insets(0, 0, 5, 5);
		gbc_y.gridx = 0;
		gbc_y.gridy = 1;
		contentPane.add(y, gbc_y);
		
		yValue = new JLabel("New label");
		GridBagConstraints gbc_yValue = new GridBagConstraints();
		gbc_yValue.insets = new Insets(0, 0, 5, 5);
		gbc_yValue.gridx = 1;
		gbc_yValue.gridy = 1;
		contentPane.add(yValue, gbc_yValue);
		
		JButton distancia = new JButton("DistanciaAlOrigen");
		GridBagConstraints gbc_distancia = new GridBagConstraints();
		gbc_distancia.insets = new Insets(0, 0, 0, 5);
		gbc_distancia.gridx = 0;
		gbc_distancia.gridy = 2;
		contentPane.add(distancia, gbc_distancia);
		
		dValue = new JLabel("New label");
		GridBagConstraints gbc_dValue = new GridBagConstraints();
		gbc_dValue.insets = new Insets(0, 0, 0, 5);
		gbc_dValue.gridx = 1;
		gbc_dValue.gridy = 2;
		contentPane.add(dValue, gbc_dValue);
		initDataBindings();
	}
	
	private void setDefaultValues() {
		// TODO Auto-generated method stub
		p1 = new ListenableRunnablePunto(2.,3.);
		p1.start();
	}
	protected void initDataBindings() {
		BeanProperty<ListenableRunnablePunto, Double> listenableRunnablePuntoBeanProperty = BeanProperty.create("x");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<ListenableRunnablePunto, Double, JLabel, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, p1, listenableRunnablePuntoBeanProperty, xValue, jLabelBeanProperty);
		autoBinding.setConverter(converter);
		autoBinding.bind();
		//
		BeanProperty<ListenableRunnablePunto, Double> listenableRunnablePuntoBeanProperty_1 = BeanProperty.create("y");
		AutoBinding<ListenableRunnablePunto, Double, JLabel, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, p1, listenableRunnablePuntoBeanProperty_1, yValue, jLabelBeanProperty);
		autoBinding_1.setConverter(converter);
		autoBinding_1.bind();
		//
		BeanProperty<ListenableRunnablePunto, Double> listenableRunnablePuntoBeanProperty_2 = BeanProperty.create("distanciaAlOrigen");
		AutoBinding<ListenableRunnablePunto, Double, JLabel, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, p1, listenableRunnablePuntoBeanProperty_2, dValue, jLabelBeanProperty);
		autoBinding_2.setConverter(converter);
		autoBinding_2.bind();
	}
}
