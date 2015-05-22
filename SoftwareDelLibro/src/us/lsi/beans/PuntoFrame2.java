package us.lsi.beans;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import java.awt.Insets;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Validator;
import javax.swing.JButton;


public class PuntoFrame2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField xText;
	private JTextField yText;
	private JLabel textField_2;
	private JLabel textField_3;
	private JButton mensaje;
	
	private ListenablePunto p1;
	private Consola c1;
	
	private Converter<Double,String> cvDoubleString = new Converter<Double,String>(){

		@Override
		public String convertForward(Double a) {
			// TODO Auto-generated method stub
			return a.toString();
		}

		@Override
		public Double convertReverse(String a) {
			// TODO Auto-generated method stub
			Double r;
			try {
				r = new Double(a);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				r = Double.NEGATIVE_INFINITY;
			}
			return r;
		}

	};
	
	@SuppressWarnings("unused")
	private Converter<String,Double> cvStringDouble = new Converter<String,Double>(){

		@Override
		public Double convertForward(String a) {
			// TODO Auto-generated method stub
			Double r;
			try {
				r = new Double(a);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				r = Double.NEGATIVE_INFINITY;
			}
			return r;
		}

		@Override
		public String convertReverse(Double a) {
			// TODO Auto-generated method stub
			return a.toString();
		}

	};

	private Validator<Double> validator = new Validator<Double>(){

		@Override
		public org.jdesktop.beansbinding.Validator<Double>.Result validate(Double a) {
			// TODO Auto-generated method stub
			Result r = null;		
			if (a == Double.NEGATIVE_INFINITY) {
				r = new Result(null, "Formato no correcto");
			} else if (a <0.){
				r = new Result(null, "Valor no correcto");
			}
			return r;
		}

	};
	
    private BindingListener listener = new BindingListener(){		
        
    	@SuppressWarnings("rawtypes")
		@Override
		public void bindingBecameBound(Binding arg0) { }

		@SuppressWarnings("rawtypes")
		@Override
		public void bindingBecameUnbound(Binding arg0) {}

		@SuppressWarnings("rawtypes")
		@Override
		public void sourceChanged(Binding arg0, PropertyStateEvent a) {}

		@SuppressWarnings("rawtypes")
		@Override
		public void syncFailed(Binding b, SyncFailure fallo) {
			mensaje.setText(fallo.getValidationResult().getDescription());
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void synced(Binding arg0) {
			mensaje.setText("Correcto");
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void targetChanged(Binding arg0, PropertyStateEvent arg1) { }
    };
		
	
    public static void main(String[] args) {
    	initGrafico();
    }


    public static void initGrafico(){
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				PuntoFrame2 frame = new PuntoFrame2();
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
	public PuntoFrame2() {
		setDefaultValues();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel x = new JLabel("X");
		GridBagConstraints gbc_x = new GridBagConstraints();
		gbc_x.insets = new Insets(0, 0, 5, 5);
		gbc_x.gridx = 0;
		gbc_x.gridy = 0;
		contentPane.add(x, gbc_x);
		
		xText = new JTextField();
		xText.setText("2.");
		GridBagConstraints gbc_xText = new GridBagConstraints();
		gbc_xText.insets = new Insets(0, 0, 5, 0);
		gbc_xText.fill = GridBagConstraints.HORIZONTAL;
		gbc_xText.gridx = 2;
		gbc_xText.gridy = 0;
		contentPane.add(xText, gbc_xText);
		xText.setColumns(10);
		
		JLabel y = new JLabel("Y");
		GridBagConstraints gbc_y = new GridBagConstraints();
		gbc_y.insets = new Insets(0, 0, 5, 5);
		gbc_y.gridx = 0;
		gbc_y.gridy = 1;
		contentPane.add(y, gbc_y);
		
		yText = new JTextField();
		yText.setText("3.");
		GridBagConstraints gbc_yText = new GridBagConstraints();
		gbc_yText.insets = new Insets(0, 0, 5, 0);
		gbc_yText.fill = GridBagConstraints.HORIZONTAL;
		gbc_yText.gridx = 2;
		gbc_yText.gridy = 1;
		contentPane.add(yText, gbc_yText);
		yText.setColumns(10);
		
		JLabel d = new JLabel("Distancia Al Origen");
		GridBagConstraints gbc_d = new GridBagConstraints();
		gbc_d.insets = new Insets(0, 0, 5, 5);
		gbc_d.gridx = 0;
		gbc_d.gridy = 2;
		contentPane.add(d, gbc_d);
		
		textField_2 = new JLabel();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 2;
		contentPane.add(textField_2, gbc_textField_2);
		
		JLabel string = new JLabel("Texto");
		GridBagConstraints gbc_string = new GridBagConstraints();
		gbc_string.insets = new Insets(0, 0, 5, 5);
		gbc_string.gridx = 0;
		gbc_string.gridy = 3;
		contentPane.add(string, gbc_string);
		
		textField_3 = new JLabel();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 3;
		contentPane.add(textField_3, gbc_textField_3);
		
		mensaje = new JButton("      ");
		GridBagConstraints gbc_mensaje = new GridBagConstraints();
		gbc_mensaje.gridx = 2;
		gbc_mensaje.gridy = 8;
		contentPane.add(mensaje, gbc_mensaje);
		initDataBindings();
	}
	
	private void setDefaultValues() {
		// TODO Auto-generated method stub
		p1 = new ListenablePunto(2.,3.);
		c1 = Consolas.createConsola();
	}
	protected void initDataBindings() {
		
		BeanProperty<ListenablePunto, Double> listenablePuntoBeanProperty_2 = BeanProperty.create("distanciaAlOrigen");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<ListenablePunto, Double, JLabel, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, p1, listenablePuntoBeanProperty_2, textField_2, jLabelBeanProperty);
		autoBinding_2.bind();
		//
		ELProperty<ListenablePunto, String> listenablePuntoEvalutionProperty = ELProperty.create("(${x},${y})");
		AutoBinding<ListenablePunto, String, JLabel, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ, p1, listenablePuntoEvalutionProperty, textField_3, jLabelBeanProperty);
		autoBinding_3.bind();
		//
		BeanProperty<ListenablePunto, Double> listenablePuntoBeanProperty = BeanProperty.create("x");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<ListenablePunto, Double, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, p1, listenablePuntoBeanProperty, xText, jTextFieldBeanProperty);
		autoBinding.setConverter(cvDoubleString);
		autoBinding.setValidator(validator);
		autoBinding.addBindingListener(listener);
		autoBinding.bind();
		//
		BeanProperty<ListenablePunto, Double> listenablePuntoBeanProperty_1 = BeanProperty.create("y");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<ListenablePunto, Double, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, p1, listenablePuntoBeanProperty_1, yText, jTextFieldBeanProperty_1);
		autoBinding_1.setConverter(cvDoubleString);
		autoBinding_1.setValidator(validator);
		autoBinding_1.addBindingListener(listener);
		autoBinding_1.bind();
		//
		BeanProperty<Consola, Integer> consolaBeanProperty = BeanProperty.create("x");
		AutoBinding<ListenablePunto, Double, Consola, Integer> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ, p1, listenablePuntoBeanProperty, c1, consolaBeanProperty);
		autoBinding_4.setConverter(new Converter<Double,Integer>(){
			@Override
			public Integer convertForward(Double a) {
				// TODO Auto-generated method stub
				return a.intValue();
			}

			@Override
			public Double convertReverse(Integer a) {
				// TODO Auto-generated method stub
				return a.doubleValue();
			}});
		autoBinding_4.bind();
	}
}
