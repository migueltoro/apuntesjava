package us.lsi.basictypes;


import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Tree<E> {
	
	public static <E> Tree<E> create() {
		return new Tree<E>();
	}

	public static <E> Tree<E> create(E label) {
		return new Tree<E>(label);
	}

	public static <E> Tree<E> create(E label, List<Tree<E>> elements) {
		return new Tree<E>(label, elements);
	}

	private E label;
	private final int INITIAL_CAPACITY = 2;
	private List<Tree<E>> elements;
	private Tree<E> parent;
		
	private Tree() {
		super();
		this.label = null;
		this.elements = Lists.newArrayListWithCapacity(INITIAL_CAPACITY);
		this.parent = null;
	}
	
	
	private Tree(E label){
		super();
		this.label = label;
		this.elements = Lists.newArrayListWithCapacity(INITIAL_CAPACITY);
		this.parent = null;
	}
		
	private Tree(E label, List<Tree<E>> elements) {
		super();
		this.label = label;
		this.elements = Lists.newArrayList(elements);
		this.parent = null;
	}
	
	public int getNumChildren(){
		int r = 0;
		if(!isEmpty()){
			r = elements.size();
		}
		return r;
	}
	
	private int size(Tree<E> t){
		int r;
		if(t== null || t.isEmpty()){
			r = 0;
		}else if(t.getNumChildren()==0){
			r = 1;
		}else {
			r = 1;
			for(int i=0; i<t.getNumChildren(); i++){
				r = r+size(t.getElement(i));
			}
		}
		return r;
	}
	
	public int size(){
		return size(this);
	}
	
	public boolean isEmpty() {
		return label == null;
	}

	public boolean isRoot() {
		return parent == null;
	}
	
	public boolean isLeaf(){	
		return isEmpty() || getNumChildren() == 0;
	}
	
	public E getLabel() {
		Preconditions.checkState(!isEmpty());
		return label;
	}

	public void setLabel(E label) {
		Preconditions.checkNotNull(label);
		this.label = label;
	}

	public Tree<E> getParent() {
		return parent;
	}

	private void setParent(Tree<E> parent) {
		Preconditions.checkNotNull(label);
		this.parent = parent;
	}

	public Tree<E> getElement(int index) {
		Preconditions.checkState(!isEmpty());
		Tree<E> r = elements.get(index);
		if(r==null){
			r = create();
		}
		return r;
	}

	public Tree<E> setElement(int index, Tree<E> element) {
		Preconditions.checkState(!isEmpty());
		element.setParent(this);
		Tree<E> r =this.elements.set(index, element);
		return r;
	}
	
	public int getDepth() {
		int r = 0;
		Tree<E> p = getParent();
		while(p!=null){
			p = p.getParent();
			r++;
		}
		return r;
	}
	
	private int getHeight(Tree<E> t){
		int r;
		if(t== null || t.isEmpty()){
			r = 0;
		}else if(t.getNumChildren()==0){
			r = 1;
		}else {
			r = -1;
			for(int i=0; i<t.getNumChildren(); i++){
				r = Math.max(r,getHeight(t.getElement(i)));
						   
			}
			r++;
		}
		return r;
	}
	
	public int getHeight() {
		return getHeight(this);
	}
	
	public boolean add(Tree<E> element){
		Preconditions.checkState(!isEmpty());
		element.setParent(this);
		boolean r = elements.add(element);
		return r;
	}
	
	public  void add(int index, Tree<E> element){
		Preconditions.checkState(!isEmpty());
		element.setParent(this);		
		elements.add(index,element);
	}
	
	public Tree<E> remove(int index){
		Preconditions.checkState(!isEmpty());
		Tree<E> r = elements.remove(index);
		r.setParent(null);
		return r;
	}
	
	public Tree<E> clone(){
		return null;
	}
		
	public String toString(){
		return toString(this);
	}
	
	private String toString(Tree<E> t){		
		String r;
		boolean prim = true;
		if(t == null || t.isEmpty()){
			r =" ";
		}else if(t.getNumChildren()==0){
			r = t.getLabel().toString();
		}else {
			r= t.getLabel().toString()+";";
			for(int i=0; i<t.getNumChildren(); i++){
				if(prim){
					r = r+toString(t.getElement(i));
					prim = false;
				}else{		
					r = r+","+toString(t.getElement(i));
				}
			}
		}
		return "("+r+")";
	}
}
