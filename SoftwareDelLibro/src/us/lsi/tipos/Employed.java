package us.lsi.tipos;

public class Employed extends Person {
 
	public static Employed create(String name, String dni, String phone,
			Double salary, Integer position) {
		return new Employed(name, dni, phone, salary, position);
	}


	private Double salary;
	private Integer position;
	
	
	private Employed(String name, String dni,String phone,Double salary, Integer position) {
		super(name,dni,phone);
		this.salary = salary;
		this.position = position;
	}


	public Double getSalary() {
		return salary;
	}


	public void setSalary(Double salary) {
		this.salary = salary;
	}


	public Integer getPosition() {
		return position;
	}


	public void setPosition(Integer position) {
		this.position = position;
	}


	@Override
	public String toString() {
		return "("+getName()+"," + position+")";
	}
	
	
	
}
