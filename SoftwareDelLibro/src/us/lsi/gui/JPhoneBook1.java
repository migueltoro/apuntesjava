package us.lsi.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import java.util.List;

import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.ELProperty;

import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class JPhoneBook1 extends JFrame {
	
//	private AutoBinding<JList, JList, JTable, JTable> Name;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSplitPane contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;
	private JTextField mobil2TextField;
	private JTable tableOfPersons;
	@SuppressWarnings("rawtypes")
	private JList categoriesList;
	
	private JButton groupsNewButton;
	private JButton groupsEditButton;
	private JButton groupsDeleteButton;
	private JButton personNewButton;
	private JButton personDeleteButton;
	private JTextField mobil1TextField;
	
	private PhoneGroups m_categories; 
	
	private List<String> namesOfCategories; 
	private JPanel leftToolBar;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel rigthToolBar;
	private JButton okButton;
	
	
	/**
	 * @return Categorias
	 */
	public PhoneGroups getCategories() {
		return m_categories;
	}

	/**
	 * @return Nombres de las categorias
	 */
	public List<String> getNamesOfCategories() {
		return namesOfCategories;
	}
	
	public static void create(final PhoneGroups categories, final List<String> names) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JPhoneBook1 frame = new JPhoneBook1(categories,names);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setDefaultValues1(PhoneGroups g, List<String> names) {
		m_categories = g;
		namesOfCategories = names;
	}
	
	@SuppressWarnings("rawtypes")
	public JPhoneBook1(PhoneGroups categories, List<String> names) {
		setDefaultValues1(categories,names);
		setTitle("Phone Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 643);
		contentPane = new JSplitPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		leftPanel = new JPanel();
		contentPane.setLeftComponent(leftPanel);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{189, 0};
		gbl_leftPanel.rowHeights = new int[]{33, 448, 0};
		gbl_leftPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		leftPanel.setLayout(gbl_leftPanel);
		
		leftToolBar = new JPanel();
		GridBagConstraints gbc_leftToolBar = new GridBagConstraints();
		gbc_leftToolBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_leftToolBar.insets = new Insets(0, 0, 5, 0);
		gbc_leftToolBar.gridx = 0;
		gbc_leftToolBar.gridy = 0;
		leftPanel.add(leftToolBar, gbc_leftToolBar);
		
		groupsNewButton = new JButton("New");
		groupsNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PhoneGroup group = new PhoneGroup("???");
				JPhoneGroupDialog1 dialog = new JPhoneGroupDialog1(namesOfCategories, group);
				dialog.setLocationRelativeTo(leftToolBar);
				dialog.setVisible(true);
				m_categories.addGroup(group);
				categoriesList.repaint();
			}
		});
		leftToolBar.add(groupsNewButton);
		
		groupsEditButton = new JButton("Edit");
		groupsEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPhoneGroupDialog1 dialog = new JPhoneGroupDialog1(namesOfCategories, m_categories.getCategories().get(categoriesList.getSelectedIndex()));
				dialog.setLocationRelativeTo(leftToolBar);
				dialog.setVisible(true);
				categoriesList.repaint();
			}
		});
		leftToolBar.add(groupsEditButton);
		
		groupsDeleteButton = new JButton("Delete");
		groupsDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_categories.removeGroup(m_categories.getCategories().get(categoriesList.getSelectedIndex()));
				categoriesList.repaint();
			}
		});
		leftToolBar.add(groupsDeleteButton);
		
		categoriesList = new JList();
		categoriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_categoriesList = new GridBagConstraints();
		gbc_categoriesList.fill = GridBagConstraints.BOTH;
		gbc_categoriesList.gridx = 0;
		gbc_categoriesList.gridy = 1;
		leftPanel.add(categoriesList, gbc_categoriesList);
		
		rightPanel = new JPanel();
		contentPane.setRightComponent(rightPanel);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[]{609, 0};
		gbl_rightPanel.rowHeights = new int[]{42, 205, 125, 0};
		gbl_rightPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_rightPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		rightPanel.setLayout(gbl_rightPanel);
		
		rigthToolBar = new JPanel();
		FlowLayout fl_rigthToolBar = (FlowLayout) rigthToolBar.getLayout();
		fl_rigthToolBar.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_rigthToolBar = new GridBagConstraints();
		gbc_rigthToolBar.anchor = GridBagConstraints.NORTH;
		gbc_rigthToolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_rigthToolBar.insets = new Insets(0, 0, 5, 0);
		gbc_rigthToolBar.gridx = 0;
		gbc_rigthToolBar.gridy = 0;
		rightPanel.add(rigthToolBar, gbc_rigthToolBar);
		
		personNewButton = new JButton("New");
		personNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PhoneGroup group = m_categories.getCategories().get(categoriesList.getSelectedIndex());
				Person person = new Person();
				group.addPerson(person);
				categoriesList.repaint();
				tableOfPersons.repaint();
			}
		});
		rigthToolBar.add(personNewButton);
		
		personDeleteButton = new JButton("Delete");
		personDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PhoneGroup group = m_categories.getCategories().get(categoriesList.getSelectedIndex());
				Person person = group.getPersons().get(tableOfPersons.getSelectedRow());
				group.removePerson(person);
				categoriesList.repaint();
				tableOfPersons.repaint();
			}
		});
		rigthToolBar.add(personDeleteButton);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		rigthToolBar.add(okButton);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		rightPanel.add(scrollPane, gbc_scrollPane);
		
		tableOfPersons = new JTable();
		tableOfPersons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableOfPersons);
		
		JPanel bottomPanel = new JPanel();
		GridBagConstraints gbc_bottomPanel = new GridBagConstraints();
		gbc_bottomPanel.fill = GridBagConstraints.BOTH;
		gbc_bottomPanel.gridx = 0;
		gbc_bottomPanel.gridy = 2;
		rightPanel.add(bottomPanel, gbc_bottomPanel);
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[]{0, 103, 0, 0};
		gbl_bottomPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_bottomPanel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		bottomPanel.setLayout(gbl_bottomPanel);
		
		JLabel nameLabel = new JLabel("Name: ");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		bottomPanel.add(nameLabel, gbc_nameLabel);
		
		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 2;
		gbc_nameTextField.gridy = 0;
		bottomPanel.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);
		
		JLabel emailLabel = new JLabel("Email: ");
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.anchor = GridBagConstraints.EAST;
		gbc_emailLabel.gridx = 0;
		gbc_emailLabel.gridy = 1;
		bottomPanel.add(emailLabel, gbc_emailLabel);
		
		emailTextField = new JTextField();
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.insets = new Insets(0, 0, 5, 0);
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.gridx = 2;
		gbc_emailTextField.gridy = 1;
		bottomPanel.add(emailTextField, gbc_emailTextField);
		emailTextField.setColumns(10);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		GridBagConstraints gbc_phoneLabel = new GridBagConstraints();
		gbc_phoneLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneLabel.anchor = GridBagConstraints.EAST;
		gbc_phoneLabel.gridx = 0;
		gbc_phoneLabel.gridy = 2;
		bottomPanel.add(phoneLabel, gbc_phoneLabel);
		
		phoneTextField = new JTextField();
		GridBagConstraints gbc_phoneTextField = new GridBagConstraints();
		gbc_phoneTextField.insets = new Insets(0, 0, 5, 0);
		gbc_phoneTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneTextField.gridx = 2;
		gbc_phoneTextField.gridy = 2;
		bottomPanel.add(phoneTextField, gbc_phoneTextField);
		phoneTextField.setColumns(10);
		
		JLabel mobileLabel1 = new JLabel("MobilePhone1:");
		GridBagConstraints gbc_mobileLabel1 = new GridBagConstraints();
		gbc_mobileLabel1.insets = new Insets(0, 0, 5, 5);
		gbc_mobileLabel1.anchor = GridBagConstraints.EAST;
		gbc_mobileLabel1.gridx = 0;
		gbc_mobileLabel1.gridy = 3;
		bottomPanel.add(mobileLabel1, gbc_mobileLabel1);
		
		mobil1TextField = new JTextField();
		GridBagConstraints gbc_mobil1TextField = new GridBagConstraints();
		gbc_mobil1TextField.insets = new Insets(0, 0, 5, 0);
		gbc_mobil1TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mobil1TextField.gridx = 2;
		gbc_mobil1TextField.gridy = 3;
		bottomPanel.add(mobil1TextField, gbc_mobil1TextField);
		mobil1TextField.setColumns(10);
		
		JLabel mobileLabel2 = new JLabel("MobilPhone2:");
		GridBagConstraints gbc_mobileLabel2 = new GridBagConstraints();
		gbc_mobileLabel2.insets = new Insets(0, 0, 5, 5);
		gbc_mobileLabel2.anchor = GridBagConstraints.EAST;
		gbc_mobileLabel2.gridx = 0;
		gbc_mobileLabel2.gridy = 4;
		bottomPanel.add(mobileLabel2, gbc_mobileLabel2);
		
		mobil2TextField = new JTextField();
		GridBagConstraints gbc_mobil2TextField = new GridBagConstraints();
		gbc_mobil2TextField.insets = new Insets(0, 0, 5, 0);
		gbc_mobil2TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mobil2TextField.gridx = 2;
		gbc_mobil2TextField.gridy = 4;
		bottomPanel.add(mobil2TextField, gbc_mobil2TextField);
		mobil2TextField.setColumns(10);
		initDataBindings();
	
	}
	@SuppressWarnings("rawtypes")
	protected void initDataBindings() {
		BeanProperty<PhoneGroups, List<PhoneGroup>> phoneGroupsBeanProperty = BeanProperty.create("categories");
		JListBinding<PhoneGroup, PhoneGroups, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, m_categories, phoneGroupsBeanProperty, categoriesList);
		//
		ELProperty<PhoneGroup, Object> phoneGroupEvalutionProperty = ELProperty.create("${name}(${personCount})");
		jListBinding.setDetailBinding(phoneGroupEvalutionProperty);
		//
		jListBinding.bind();
		//
		BeanProperty<JList, List<Person>> jListBeanProperty = BeanProperty.create("selectedElement.persons");
		JTableBinding<Person, JList, JTable> jTableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ, categoriesList, jListBeanProperty, tableOfPersons);
		//
		BeanProperty<Person, String> personBeanProperty = BeanProperty.create("name");
		jTableBinding.addColumnBinding(personBeanProperty).setColumnName("Name");
		//
		BeanProperty<Person, String> personBeanProperty_1 = BeanProperty.create("email");
		jTableBinding.addColumnBinding(personBeanProperty_1).setColumnName("Email");
		//
		BeanProperty<Person, String> personBeanProperty_2 = BeanProperty.create("phone");
		jTableBinding.addColumnBinding(personBeanProperty_2).setColumnName("Phone");
		//
		BeanProperty<Person, String> personBeanProperty_3 = BeanProperty.create("mobilePhone1");
		jTableBinding.addColumnBinding(personBeanProperty_3).setColumnName("MobilePhone1");
		//
		BeanProperty<Person, String> personBeanProperty_4 = BeanProperty.create("mobilePhone2");
		jTableBinding.addColumnBinding(personBeanProperty_4).setColumnName("MobilePhone2");
		//
		BeanProperty<Person, String> personBeanProperty_5 = BeanProperty.create("string");
		jTableBinding.addColumnBinding(personBeanProperty_5).setColumnName("String");
		//
		jTableBinding.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty = BeanProperty.create("selectedElement.name");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableOfPersons, jTableBeanProperty, nameTextField, jTextFieldBeanProperty);
		autoBinding_2.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_1 = BeanProperty.create("selectedElement.email");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableOfPersons, jTableBeanProperty_1, emailTextField, jTextFieldBeanProperty_1);
		autoBinding.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_2 = BeanProperty.create("selectedElement.phone");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableOfPersons, jTableBeanProperty_2, phoneTextField, jTextFieldBeanProperty_2);
		autoBinding_1.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_3 = BeanProperty.create("selectedElement.mobilePhone1");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableOfPersons, jTableBeanProperty_3, mobil1TextField, jTextFieldBeanProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_4 = BeanProperty.create("selectedElement.mobilePhone2");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableOfPersons, jTableBeanProperty_4, mobil2TextField, jTextFieldBeanProperty_4);
		autoBinding_4.bind();
		//
		ELProperty<JList, Object> jListEvalutionProperty_1 = ELProperty.create("${selectedElement != null}");
		BeanProperty<JButton, Boolean> jButtonBeanProperty = BeanProperty.create("enabled");
		AutoBinding<JList, Object, JButton, Boolean> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ, categoriesList, jListEvalutionProperty_1, groupsDeleteButton, jButtonBeanProperty);
		autoBinding_6.bind();
		//
		ELProperty<JTable, Object> jTableEvalutionProperty = ELProperty.create("${selectedElement != null}");
		AutoBinding<JTable, Object, JButton, Boolean> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, tableOfPersons, jTableEvalutionProperty, personDeleteButton, jButtonBeanProperty);
		autoBinding_7.bind();
		//
		ELProperty<JList, Object> jListEvalutionProperty = ELProperty.create("${selectedElement != null}");
		AutoBinding<JList, Object, JButton, Boolean> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ, categoriesList, jListEvalutionProperty, groupsEditButton, jButtonBeanProperty);
		autoBinding_5.bind();
	}
}
