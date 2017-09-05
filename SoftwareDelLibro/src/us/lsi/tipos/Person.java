package us.lsi.tipos;

public class Person {
	
	public static Person create(String name, String dni,String phone) {
		return new Person(name, dni, phone);
	}

	private String name;
	private String dni;
	private String phone;
	
	protected Person(String name, String dni, String phone) {
		super();
		this.name = name;
		this.dni = dni;
		this.phone = phone;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}


	public String getDni() {
		return dni;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return name;
	}


	
	
	
}
