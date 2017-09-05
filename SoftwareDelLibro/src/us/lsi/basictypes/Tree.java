package us.lsi.basictypes;


import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import us.lsi.algoritmos.AbstractAlgoritmo;

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

	@SafeVarargs
	public static <E> Tree<E> create(E label, Tree<E>... elements) {
		return new Tree<E>(label, elements);
	}
	/**
	 * @param <E> El tipo de las etiquetas
	 * @param tree Una árbol
	 * @return Un árbol copia 
	 */
	public static <E> Tree<E> create(Tree<E> tree){
		return Tree.copia(tree);
	}
	/**
	 * @param <E> El tipo de las etiquetas
	 * @param s Un string
	 * @param fLabel Una función que transforma una string en un elemento del tipo E
	 * @return Un árbol construido a partir de la string
	 */
	public static <E> Tree<E> create(String s, Function<String,E> fLabel){
		TokenizeString ts = TokenizeString.create(s);
		Tree<E> t = Tree.leeTree(ts,fLabel);		
		return t;
	}
	
	/**
	 * @param <E> El tipo de las etiquetas
	 * @param ls Una lista
	 * @return Un árbol cuya raiz contiene el primer elemento de la lista, 
	 * su primer hijo el segundo elemento, etc. 
	 */
	public static <E> Tree<E> create(List<E> ls){
		return Tree.create(ls,0);
	}
		
	private static <E> Tree<E> create(List<E> ls, int i){
		Tree<E> r = null;
		if(i<ls.size()){
			r = Tree.create(ls.get(i),Tree.create(ls, i+1));
		} else{
			r = Tree.create();
		}
		return r;
	}
	
	private E label;
	private Integer id;
	private final int INITIAL_CAPACITY = 4;
	private List<Tree<E>> elements;
	private Tree<E> parent;
		
	private Tree() {
		super();
		this.id = null;
		this.label = null;
		this.elements = Lists.newArrayListWithCapacity(INITIAL_CAPACITY);
		this.parent = null;
	}
	
	
	private Tree(E label){
		super();
		this.id = null;
		this.label = label;
		this.elements = Lists.newArrayListWithCapacity(INITIAL_CAPACITY);
		this.parent = null;
	}
		
	private Tree(E label, List<Tree<E>> elements) {
		super();
		this.id = null;
		this.label = label;
		this.elements = Lists.newArrayList(elements);
		this.elements.stream().forEach(x->x.parent = this);
		this.parent = null;
	}
	
	@SafeVarargs
	private Tree(E label, Tree<E>... elements) {
		super();
		this.label = label;
		this.elements = Arrays.asList(elements);
		this.elements.stream().forEach(x->x.parent = this);
		this.parent = null;
	}
	
	private static <E> Tree<E> leeTree(TokenizeString s,Function<String, E> fLabel) {
		Tree<E> r = null;		
		List<Tree<E>> ls = Lists.newArrayList();
		E label = null;
		Tree<E> t = null;
		Character c = s.nextCharIgnoringSpaces();
		s.matchCaracter('(');
		c = s.nextCharIgnoringSpaces();
		if (c.equals(')')) {
			s.matchCaracter(')');
			r = Tree.create();
		} else {
			String sr = s.copiarHasta('!');
			label = fLabel.apply(sr);
			c = s.nextCharIgnoringSpaces();
			if (c.equals(')')) {				
				r = Tree.create(label);	
				s.matchCaracter(')');
			} else if(c.equals('(')){
				t = leeTree(s, fLabel);
				ls.add(t);
				c = s.nextCharIgnoringSpaces();
				while(c.equals(',')) {
					s.matchCaracter(',');
					t = leeTree(s, fLabel);
					ls.add(t);
					c = s.nextCharIgnoringSpaces();
				};
				s.matchCaracter(')');
				r = Tree.create(label, ls);
			}			
		}
		return r;
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
		int r = -1;
		if(t!= null && !t.isEmpty()){
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
		element.parent = this;
		return r;
	}
	
	public  void add(int index, Tree<E> element){
		Preconditions.checkState(!isEmpty());
		element.setParent(this);		
		elements.add(index,element);
		element.parent = this;
	}
	
	public Tree<E> remove(int index){
		Preconditions.checkState(!isEmpty());
		Tree<E> r = elements.remove(index);
		r.setParent(null);
		return r;
	}
	
	private static <E> Tree<E> copia(Tree<E> tree){
		Tree<E> r = null;
		if(tree.isEmpty()){
			r = Tree.create();
		}else if(tree.isLeaf()){
			r = Tree.create(tree.getLabel());
		}else{
			r = Tree.create(tree.getLabel());
			for(Tree<E> t:tree.elements){
				r.add(copia(t));
			}			
		}
		return r;
	}
	
	private static Integer nId = 0;
	
	private static <E> void asignarNullAlId(Tree<E> t){
		if(t==null){
		} else if(t.isLeaf()) {
			t.id = null;
		} else for (int i = 0; i < t.getNumChildren(); i++) {
			t.id = null;
			asignarNullAlId(t.getElement(i));
		}
	}
	
	private static <E> String label(Tree<E> t) {
		String s = "    " + "\"" + t.id + "\"";
		if (t.isEmpty()) {
			s = s + " [label=\"" + "" + "\"]";
		} else {
			s = s + " [label=\"" + t.getLabel() + "\"]";
		}
		return s + ";";
	}

	
	private static <E> String arista(Tree<E> t,int index){
		return "    "+"\""+t.id+"\""+" -> "+"\""+t.getElement(index).id +"\""+
				" [label=\"" + index + "\"];";
	}
	
	public void toDOT(String file, String titulo){
		Tree.toDOT(this,file,titulo);
	}
	
	private static <E> void toDOT(Tree<E> t, String file, String titulo){
		AbstractAlgoritmo.setFile(file);
		AbstractAlgoritmo.getFile().println("digraph "+titulo+" {  \n size=\"100,100\"; ");	
		asignarNullAlId(t);
		Tree.nId = 0;
		Tree.toDOTVertex(t);
		Tree.toDOTEdge(t);
		AbstractAlgoritmo.getFile().println("}");
	}
	
	private static <E> void toDOTVertex(Tree<E> t) {
		if (t != null && t.id==null) {
			t.id = Tree.nId;
			Tree.nId++;
			AbstractAlgoritmo.getFile().println(Tree.label(t));
			for (int i = 0; i < t.getNumChildren(); i++) {				
				Tree.toDOTVertex(t.getElement(i));
			}
		}
	}

	private static <E> void toDOTEdge(Tree<E> t){
		if (t != null && !t.isLeaf()) {
			for (int i = 0; i < t.getNumChildren(); i++) {				
				Tree<E> th = t.getElement(i);
				if(th==null) continue;
				AbstractAlgoritmo.getFile().println(Tree.arista(t,i));
				Tree.toDOTEdge(t.getElement(i));
			}
		}
	}
	
	
	public String toString(){
		return toString(this);
	}
	
	private String toString(Tree<E> t){		
		String r;
		boolean prim = true;
		if(t == null || t.isEmpty()){
			r ="";
		}else if(t.getNumChildren()==0){
			r = t.getLabel().toString()+"!";
		}else {
			r= t.getLabel().toString()+"!";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tree))
			return false;
		Tree<?> other = (Tree<?>) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		else {
			return IntStream.range(0,elements.size()).boxed()
					.allMatch(i->elements.get(i).equals(other.elements.get(i)));
		}		
		return true;
	}
	
	
}
