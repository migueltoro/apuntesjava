package us.lsi.gui;

import java.util.List;



import java.util.function.Function;

import us.lsi.stream.Stream2;




import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;


public class AppPhoneGroups {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		newGroups();		
//		JPhoneGroupDialog1.create(nameOfCategories,categories.getGroups().get(0));
		JPhoneBook1.create(categories,nameOfCategories);		
	}
		
	public static void newGroups() {
		final Function<String, Person> f = (String s) -> {
			String[] ps = s.split(",");
			Preconditions.checkArgument(ps.length == 5);
			return new Person(ps[0], ps[1], ps[2], ps[3], ps[4]);
		};

		PhoneGroup group1 = new PhoneGroup("Mecanicos");
		PhoneGroup group2 = new PhoneGroup("Programadores");
		Stream2.fromFile("mecanicos.txt")
				.<Person> map(f)
				.forEach((Person x) -> group1.addPerson(x));
		Stream2.fromFile("programadores.txt")
				.<Person> map(f)
				.forEach((Person x) -> group1.addPerson(x));

		categories.addGroup(group1);
		nameOfCategories.add(group1.getName());

		categories.addGroup(group2);
		nameOfCategories.add(group2.getName());
		nameOfCategories.add("Empresarios");
		nameOfCategories.add("Otros");
	}

	public static PhoneGroups categories = new PhoneGroups();
	public static List<String> nameOfCategories = Lists.newArrayList();

	public static PhoneGroups getGroups() {
		return categories;
	}

}
