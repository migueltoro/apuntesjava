package us.lsi.concurrent.filosofos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import us.lsi.concurrent.filosofos.Filosofo.Estado;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class JFilosofos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private JButton t0Label;
	private JButton t1Label;
	private JButton t2Label;
	private JButton t3Label;
	private JButton t4Label;
	private JButton t5Label;
	private JButton f0Label;
	private JButton f1Label;
	private JButton f2Label;
	private JButton f3Label;
	private JButton f4Label;
	private JButton f5Label;
	private JButton endButton;

	
	private Tenedor tenedor0;
	private Tenedor tenedor1;
	private Tenedor tenedor2;
	private Tenedor tenedor3;
	private Tenedor tenedor4;
	private Tenedor tenedor5;
	
	private Filosofo filosofo0;
	private Filosofo filosofo1;
	private Filosofo filosofo2;
	private Filosofo filosofo3;
	private Filosofo filosofo4;
	private Filosofo filosofo5;

	private JTextField textField;
	
	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFilosofos frame = new JFilosofos();
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
	public JFilosofos() {
		setDefaultValues();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		f0Label = new JButton("    F0");
		GridBagConstraints gbc_f0Label = new GridBagConstraints();
		gbc_f0Label.insets = new Insets(0, 0, 5, 5);
		gbc_f0Label.gridx = 0;
		gbc_f0Label.gridy = 2;
		contentPane.add(f0Label, gbc_f0Label);
		
		f1Label = new JButton("    F1");
		GridBagConstraints gbc_f1Label = new GridBagConstraints();
		gbc_f1Label.insets = new Insets(0, 0, 5, 5);
		gbc_f1Label.gridx = 1;
		gbc_f1Label.gridy = 2;
		contentPane.add(f1Label, gbc_f1Label);
		
		f2Label = new JButton("    F2");
		GridBagConstraints gbc_f2Label = new GridBagConstraints();
		gbc_f2Label.insets = new Insets(0, 0, 5, 5);
		gbc_f2Label.gridx = 2;
		gbc_f2Label.gridy = 2;
		contentPane.add(f2Label, gbc_f2Label);
		
		f3Label = new JButton("    F3");
		GridBagConstraints gbc_f3Label = new GridBagConstraints();
		gbc_f3Label.insets = new Insets(0, 0, 5, 5);
		gbc_f3Label.gridx = 3;
		gbc_f3Label.gridy = 2;
		contentPane.add(f3Label, gbc_f3Label);
		
		f4Label = new JButton("    F4");
		GridBagConstraints gbc_f4Label = new GridBagConstraints();
		gbc_f4Label.insets = new Insets(0, 0, 5, 5);
		gbc_f4Label.gridx = 4;
		gbc_f4Label.gridy = 2;
		contentPane.add(f4Label, gbc_f4Label);
		
		f5Label = new JButton("    F5");
		GridBagConstraints gbc_f5Label = new GridBagConstraints();
		gbc_f5Label.insets = new Insets(0, 0, 5, 5);
		gbc_f5Label.gridx = 5;
		gbc_f5Label.gridy = 2;
		contentPane.add(f5Label, gbc_f5Label);
		
		t0Label = new JButton("    T0");
		GridBagConstraints gbc_t0Label = new GridBagConstraints();
		gbc_t0Label.insets = new Insets(0, 0, 5, 5);
		gbc_t0Label.gridx = 0;
		gbc_t0Label.gridy = 5;
		contentPane.add(t0Label, gbc_t0Label);
		
		t1Label = new JButton("    T1");
		GridBagConstraints gbc_t1Label = new GridBagConstraints();
		gbc_t1Label.insets = new Insets(0, 0, 5, 5);
		gbc_t1Label.gridx = 1;
		gbc_t1Label.gridy = 5;
		contentPane.add(t1Label, gbc_t1Label);
		
		t2Label = new JButton("    T2");
		GridBagConstraints gbc_t2Label = new GridBagConstraints();
		gbc_t2Label.insets = new Insets(0, 0, 5, 5);
		gbc_t2Label.gridx = 2;
		gbc_t2Label.gridy = 5;
		contentPane.add(t2Label, gbc_t2Label);
		
		t3Label = new JButton("    T3");
		GridBagConstraints gbc_t3Label = new GridBagConstraints();
		gbc_t3Label.insets = new Insets(0, 0, 5, 5);
		gbc_t3Label.gridx = 3;
		gbc_t3Label.gridy = 5;
		contentPane.add(t3Label, gbc_t3Label);
		
		t4Label = new JButton("    T4");
		GridBagConstraints gbc_t4Label = new GridBagConstraints();
		gbc_t4Label.insets = new Insets(0, 0, 5, 5);
		gbc_t4Label.gridx = 4;
		gbc_t4Label.gridy = 5;
		contentPane.add(t4Label, gbc_t4Label);
		
		t5Label = new JButton("    T5");
		GridBagConstraints gbc_t5Label = new GridBagConstraints();
		gbc_t5Label.insets = new Insets(0, 0, 5, 5);
		gbc_t5Label.gridx = 5;
		gbc_t5Label.gridy = 5;
		contentPane.add(t5Label, gbc_t5Label);
		
		endButton = new JButton("END");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionEnd();
			}			
		});
		
		textField = new JTextField();
		textField.setText("10");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 7;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		GridBagConstraints gbc_endButton = new GridBagConstraints();
		gbc_endButton.insets = new Insets(0, 0, 5, 5);
		gbc_endButton.gridx = 5;
		gbc_endButton.gridy = 7;
		contentPane.add(endButton, gbc_endButton);
		initDataBindings();
	}

	private void actionEnd() {
		// TODO Auto-generated method stub
		Filosofos.stop();
	}
	
	private void setDefaultValues() {

		Tenedores.init();
		Filosofos.init();		
		tenedor0 = Tenedores.getTenedores()[0];
		tenedor1 = Tenedores.getTenedores()[1];
		tenedor2 = Tenedores.getTenedores()[2];
		tenedor3 = Tenedores.getTenedores()[3];
		tenedor4 = Tenedores.getTenedores()[4];
		tenedor5 = Tenedores.getTenedores()[5];
		filosofo0 = Filosofos.getFilosofos()[0];
		filosofo1 = Filosofos.getFilosofos()[1];
		filosofo2 = Filosofos.getFilosofos()[2];
		filosofo3 = Filosofos.getFilosofos()[3];
		filosofo4 = Filosofos.getFilosofos()[4];
		filosofo5 = Filosofos.getFilosofos()[5];		
		Filosofos.start();
	}
	
	protected void initDataBindings() {
		
		BeanProperty<Filosofo, Estado> filosofoBeanProperty = BeanProperty.create("estado");
		BeanProperty<JButton, String> jButtonBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<Filosofo, Estado, JButton, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ, filosofo0, filosofoBeanProperty, f0Label, jButtonBeanProperty_1);
		autoBinding_6.setConverter(new ConvertEstFilToTex());
		autoBinding_6.bind();
		//
		AutoBinding<Filosofo, Estado, JButton, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, filosofo1, filosofoBeanProperty, f1Label, jButtonBeanProperty_1);
		autoBinding_7.setConverter(new ConvertEstFilToTex());
		autoBinding_7.bind();
		//
		AutoBinding<Filosofo, Estado, JButton, String> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ, filosofo2, filosofoBeanProperty, f2Label, jButtonBeanProperty_1);
		autoBinding_8.setConverter(new ConvertEstFilToTex());
		autoBinding_8.bind();
		//
		AutoBinding<Filosofo, Estado, JButton, String> autoBinding_9 = Bindings.createAutoBinding(UpdateStrategy.READ, filosofo3, filosofoBeanProperty, f3Label, jButtonBeanProperty_1);
		autoBinding_9.setConverter(new ConvertEstFilToTex());
		autoBinding_9.bind();
		//
		AutoBinding<Filosofo, Estado, JButton, String> autoBinding_10 = Bindings.createAutoBinding(UpdateStrategy.READ, filosofo4, filosofoBeanProperty, f4Label, jButtonBeanProperty_1);
		autoBinding_10.setConverter(new ConvertEstFilToTex());
		autoBinding_10.bind();
		//
		AutoBinding<Filosofo, Estado, JButton, String> autoBinding_11 = Bindings.createAutoBinding(UpdateStrategy.READ, filosofo5, filosofoBeanProperty, f5Label, jButtonBeanProperty_1);
		autoBinding_11.setConverter(new ConvertEstFilToTex());
		autoBinding_11.bind();
		//
		BeanProperty<Tenedor, Tenedor.Estado> tenedorBeanProperty = BeanProperty.create("estado");
		AutoBinding<Tenedor, Tenedor.Estado, JButton, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, tenedor0, tenedorBeanProperty, t0Label, jButtonBeanProperty_1);
		autoBinding.setConverter(new ConvertEstTenToTex());
		autoBinding.bind();
		//
		AutoBinding<Tenedor, Tenedor.Estado, JButton, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, tenedor1, tenedorBeanProperty, t1Label, jButtonBeanProperty_1);
		autoBinding_1.setConverter(new ConvertEstTenToTex());
		autoBinding_1.bind();
		//
		AutoBinding<Tenedor, Tenedor.Estado, JButton, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, tenedor2, tenedorBeanProperty, t2Label, jButtonBeanProperty_1);
		autoBinding_2.setConverter(new ConvertEstTenToTex());
		autoBinding_2.bind();
		//
		AutoBinding<Tenedor, Tenedor.Estado, JButton, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ, tenedor3, tenedorBeanProperty, t3Label, jButtonBeanProperty_1);
		autoBinding_3.setConverter(new ConvertEstTenToTex());
		autoBinding_3.bind();
		//
		AutoBinding<Tenedor, Tenedor.Estado, JButton, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ, tenedor4, tenedorBeanProperty, t4Label, jButtonBeanProperty_1);
		autoBinding_4.setConverter(new ConvertEstTenToTex());
		autoBinding_4.bind();
		//
		AutoBinding<Tenedor, Tenedor.Estado, JButton, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ, tenedor5, tenedorBeanProperty, t5Label, jButtonBeanProperty_1);
		autoBinding_5.setConverter(new ConvertEstTenToTex());
		autoBinding_5.bind();
		//
		
	}
}
