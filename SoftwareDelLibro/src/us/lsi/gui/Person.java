package us.lsi.gui;

public class Person extends AbstractModelObject {
	private String m_name;
	private String m_email;
	private String m_phone;
	private String m_mobilePhone1;
	private String m_mobilePhone2;

	public Person() {
		m_name = "???????";
	}

	public Person(String name, String email, String phone, String phone1, String phone2) {
		m_name = name;
		m_email = email;
		m_phone = phone;
		m_mobilePhone1 = phone1;
		m_mobilePhone2 = phone2;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		String oldValue = m_name;
		String oldValueString = toString();
		m_name = name;
		firePropertyChange("name", oldValue, m_name);
		firePropertyChange("string", oldValueString, toString());
	}

	public String getEmail() {
		return m_email;
	}

	public void setEmail(String email) {
		String oldValue = m_email;
		String oldValueString = toString();
		m_email = email;
		firePropertyChange("email", oldValue, m_email);
		firePropertyChange("string", oldValueString, toString());
	}

	public String getPhone() {
		return m_phone;
	}

	public void setPhone(String phone) {
		String oldValue = m_phone;
		String oldValueString = toString();
		m_phone = phone;
		firePropertyChange("phone", oldValue, m_phone);
		firePropertyChange("string", oldValueString, toString());
	}

	public String getMobilePhone1() {
		return m_mobilePhone1;
	}

	public void setMobilePhone1(String phone1) {
		String oldValue = m_mobilePhone1;
		String oldValueString = toString();
		m_mobilePhone1 = phone1;
		firePropertyChange("mobilePhone1", oldValue, m_mobilePhone1);
		firePropertyChange("string", oldValueString, toString());
	}

	public String getMobilePhone2() {
		return m_mobilePhone2;
	}

	public void setMobilePhone2(String phone2) {
		String oldValue = m_mobilePhone2;
		String oldValueString = toString();
		m_mobilePhone2 = phone2;
		firePropertyChange("mobilePhone2", oldValue, m_mobilePhone2);
		firePropertyChange("string", oldValueString, toString());
	}

	public String toString(){
		return m_name+","+m_email+","+m_phone+","+m_mobilePhone1+","+m_mobilePhone2;
	}
	
	public String getString(){
		return toString();
	}
}
