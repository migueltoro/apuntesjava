package us.lsi.gui;
import java.util.*;

import com.google.common.collect.Ordering;

public class PhoneGroups extends AbstractModelObject {
	
	
	private List<PhoneGroup> categories;
	private static Ordering<PhoneGroup> ordGroup = new Ordering<PhoneGroup>(){
		public int compare(PhoneGroup g1, PhoneGroup g2) {
			return g1.getPersonCount().compareTo(g2.getPersonCount());
		}		
	};

	
	public PhoneGroups() {
		super();
		this.categories = new ArrayList<PhoneGroup>();;
	}

	public void addGroup(PhoneGroup group) {
		List<PhoneGroup> oldValue = categories;
		PhoneGroup oldValueMejor = categories.isEmpty()?null:getMejorCategoria();
		categories = new ArrayList<PhoneGroup>(categories);
		categories.add(group);
		firePropertyChange("categories", oldValue, categories);
		firePropertyChange("mejorcategoria", oldValueMejor, getMejorCategoria());
	}

	public void removeGroup(PhoneGroup group) {
		List<PhoneGroup> oldValue = categories;
		PhoneGroup oldValueMejor = categories.isEmpty()?null:getMejorCategoria();
		categories = new ArrayList<PhoneGroup>(categories);
		categories.remove(group);
		firePropertyChange("categories", oldValue, categories);
		firePropertyChange("mejorcategoria", oldValueMejor, getMejorCategoria());
	}

	public List<PhoneGroup> getCategories() {
		return categories;
	}
	
	public PhoneGroup getMejorCategoria() {
		return ordGroup.max(categories);		
	}

}
