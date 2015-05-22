package us.lsi.gui;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JPhoneGroupDialog1 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final PhoneGroup phoneGroup;
	private final List<String> names;
	private JLabel nameLabel;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JButton okButton;

	
	public static void create(List<String> names, PhoneGroup phoneGroup) {		
				try {
					JPhoneGroupDialog1 dialog = new JPhoneGroupDialog1(names,phoneGroup);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	
	@SuppressWarnings("rawtypes")
	public JPhoneGroupDialog1(List<String> names, PhoneGroup phoneGroup) {
		this.phoneGroup = phoneGroup;
		this.names = names;		
		setBounds(100, 100, 450, 173);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{434, 0};
		gridBagLayout.rowHeights = new int[]{229, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.fill = GridBagConstraints.BOTH;
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 0;
		getContentPane().add(contentPanel, gbc_contentPanel);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{101, 46, 0};
		gbl_contentPanel.rowHeights = new int[]{14, 0, 66, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			nameLabel = new JLabel("Name");
			GridBagConstraints gbc_nameLabel = new GridBagConstraints();
			gbc_nameLabel.anchor = GridBagConstraints.EAST;
			gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_nameLabel.gridx = 0;
			gbc_nameLabel.gridy = 0;
			contentPanel.add(nameLabel, gbc_nameLabel);
		}
		{
			comboBox = new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JPanel panel = new JPanel();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 2;
			contentPanel.add(panel, gbc_panel);
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				panel.add(okButton);
			}
		}
		initDataBindings();
	}
	@SuppressWarnings("rawtypes")
	protected void initDataBindings() {
		JComboBoxBinding<String, List<String>, JComboBox> jComboBinding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, names, comboBox, "ComboxBinding");
		jComboBinding.bind();
		//
		BeanProperty<PhoneGroup, String> phoneGroupBeanProperty = BeanProperty.create("name");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty_1 = BeanProperty.create("selectedItem");
		AutoBinding<PhoneGroup, String, JComboBox, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, phoneGroup, phoneGroupBeanProperty, comboBox, jComboBoxBeanProperty_1, "Selected");
		autoBinding.bind();
	}
}
