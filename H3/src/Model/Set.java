package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Set<E extends Citizen> implements Serializable {
	private ArrayList<E> list;
	
	public Set() {
		list = new ArrayList<E>();
	}
	
	public void add(E c) throws Exception { // Returns TRUE - when object added, False - when inside		
		if (!contains(c)) {
			list.add(c);
		} else
			throw new Exception("There is a citizen with the same ID");
	}
	
	public boolean contains(E c) {	//Returns TRUE - when object on arraylist
		for (int i=0; i<list.size(); i++) {
			if (c.equals(get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public E get(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
