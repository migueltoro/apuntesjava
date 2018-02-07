package us.lsi.tiposrecursivos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;

public class Tree<E> {
	
	private static Tree<?> emptyTree = new Tree<>();
	
	@SuppressWarnings("unchecked")
	public static <R> Tree<R> empty() {
		return (Tree<R>)emptyTree;
	}

	public static <R> Tree<R> leaf(R label) {
		return new Tree<R>(label);
	}

	public static <R> Tree<R> nary(R label, List<Tree<R>> elements) {
		return new Tree<R>(label, elements);
	}

	@SafeVarargs
	public static <R> Tree<R> nary(R label, Tree<R>... elements) {
		return new Tree<R>(label, Arrays.asList(elements));
	}
	
	public static Tree<String> tree(String s){
		Tokenizer t = Tokenizer.create(s);
		return Tree.tree(t);
	}

	
	private E label;
	private Integer id;
	private List<Tree<E>> elements;
		
	private Tree() {
		super();
		this.id = null;
		this.label = null;
		this.elements = null;
	}
	
	private Tree(E label){
		super();
		this.id = null;
		this.label = label;
		this.elements = new ArrayList<>();
	}
		
	private Tree(E label, List<Tree<E>> elements) {
		super();
		this.id = null;
		this.label = label;
		this.elements = new ArrayList<>(elements);
	}
	
	private static Tree<String> tree(Tokenizer tk) {
		Tree<String> r= null;;
		switch (tk.seeNextTokenType()) {
		case Integer:
		case Double:
			String label = tk.matchTokenTypes(TokenType.VariableIdentifier, TokenType.Integer,TokenType.Double); 
			List<Tree<String>> elements = new ArrayList<>();
			Tree<String> t;
			tk.matchTokens("(");
			if(!tk.seeNextToken().equals(")")){
				t = tree(tk);
				elements.add(t);
				while(tk.seeNextToken().equals(",")){
					tk.matchTokens(",");
					t = tree(tk);
					elements.add(t);
				}
			}			
			tk.matchTokens(")");
			r = Tree.nary(label, elements);
			break;
		case Separator:
			tk.matchTokens("(");
			tk.matchTokens(")");
			r = Tree.empty();
			break;
		default:
			Preconditions.checkState(false, String.format("Token %s no reconocido en la posición %d",tk.seeNextTokenType().toString(),tk.getPosition()));
		}
		return r;
	}
	
	
	/**
	 * @return Verdadero si el árbol es vacio. 
	 */
	public boolean isEmpty() {
		return label == null && this.elements == null;
	}
	/**
	 * @return Verdadero si el árbol es hoja. 
	 */
	public boolean isLeaf(){	
		return this.label !=null && this.elements != null && this.elements.size()==0;
	}
	
	/**
	 * @return Verdadero si el árbol es nario. 
	 */
	public boolean isNary(){
		return this.label !=null && this.elements != null && this.elements.size()>0;
	}
	
	public E getLabel() {
		Preconditions.checkState(!isEmpty());
		return label;
	}

	public void setLabel(E label) {
		Preconditions.checkState(!isEmpty());
		Preconditions.checkNotNull(label);
		this.label = label;
	}

	public Tree<E> getElement(int index) {
		Preconditions.checkState(isNary());
		Preconditions.checkElementIndex(index, elements.size());
		return elements.get(index);
	}

	public Tree<E> setElement(int index, Tree<E> element) {
		Preconditions.checkState(isNary());
		Preconditions.checkNotNull(element);
		return elements.set(index, element);
	}

	public int getNumElements(){
		int r = 0;
		if(!(isEmpty() || isLeaf())){
			r = elements.size();
		}
		return r;
	}
	
	public int size(){
		int r;
		if(isEmpty()){
			r = 0;
		} else if(isLeaf()){
			r = 1;
		} else {
			r = 1+(int)elements.stream().mapToInt(x->x.size()).count();
		}
		return r;
	}
	
	public int getHeight(){
		int r;
		if((isEmpty() || isLeaf())){
			r = 0;
		} else {
			r = 1+(int)elements.stream().mapToInt(x->x.getHeight()).max().orElse(0);
		}
		return r;
	}
	
	public Tree<E> add(Tree<E> element){
		Preconditions.checkState(!isEmpty());
		List<Tree<E>> nElements = elements.stream().map(x->x.copy()).collect(Collectors.toList());
		nElements.add(element);
		return Tree.nary(label, nElements);
	}
	
	public  Tree<E> add(int index, Tree<E> element){
		Preconditions.checkState(!isEmpty());		
		List<Tree<E>> nElements = elements.stream().map(x->x.copy()).collect(Collectors.toList());
		nElements.add(index,element);
		return Tree.nary(label, nElements);
	}
	
	public Tree<E> remove(int index){
		Preconditions.checkState(!isEmpty());
		List<Tree<E>> nElements = elements.stream().map(x->x.copy()).collect(Collectors.toList());
		nElements.remove(index);
		return Tree.nary(label, nElements);
	}
	
	public Tree<E> copy(){
		Tree<E> r;
		if(isEmpty()){
			r = this;
		}else if(isLeaf()){
			r = Tree.leaf(label);
		}else{
			List<Tree<E>> nElements = elements.stream().map(x->x.copy()).collect(Collectors.toList());	
			return Tree.nary(label, nElements);
		}
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public <R> Tree<R> copy(Function<E,R> f){
		Tree<R> r;
		if(isEmpty()){
			r = (Tree<R>) this;
		}else if(isLeaf()){
			r = Tree.leaf(f.apply(label));
		}else{
			List<Tree<R>> nElements = elements.stream().map(x->x.copy(f)).collect(Collectors.toList());	
			return Tree.nary(f.apply(label), nElements);
		}
		return r;
	}
	/**
	 * @return Un árbol que es la imagen especular de this
	 */
	public Tree<E> getReverse() {
		Tree<E> r;
		if(isEmpty()){
			r = Tree.empty();
		} else if(isLeaf()){
			r = Tree.leaf(label);
		} else {
			List<Tree<E>> nElements = 
					Lists2.reverse(elements)
					.stream()
					.map(x->x.getReverse())
					.collect(Collectors.toList());		
			r = Tree.nary(label, nElements);
		}
		return r;
	}
	
	private static Integer nId = 0;
	
	private void asignarNullAlId(){
		if(isEmpty() || isLeaf()) {
			id = null;
		} else {
			elements.stream().forEach(x->x.asignarNullAlId());
		}
	}

	private void head(String file, String titulo){		
		AbstractAlgoritmo.setFile(file);
		AbstractAlgoritmo.getFile().println("digraph "+titulo+" {  \n    size=\"100,100\"; ");	
		asignarNullAlId();
	}
	
	private void writeLabel(String file) {
		String s = "    \"%d\" [label=\"%s\"];";
		AbstractAlgoritmo.getFile().println(String.format(s,id,isEmpty()?"":getLabel().toString()));
	}
	private void writeEdge(String file, int index){
		String s = "    \"%d\" -> \"%d\" [label=\"%d\"];";
		AbstractAlgoritmo.getFile().println(String.format(s,id,getElement(index).id,index));
	}
	
	public void toDOT(String file, String titulo) {
		if (id == null) {
			id = Tree.nId;
			Tree.nId++;
		}
		if (id == 0)
			head(file, titulo);
		writeLabel(file);
		if (!isEmpty()) {
			elements.stream().forEach(x -> x.toDOT(file, titulo));
			for (int i = 0; i < elements.size(); i++) {
				if (getElement(i).id == null) {
					getElement(i).id = Tree.nId;
					Tree.nId++;
				}
				writeEdge(file, i);
			}
		}
		if (id == 0) {
			AbstractAlgoritmo.getFile().println("}");
			Tree.nId = 0;
		}
	}
	
	/**
	 * @return Una lista con el recorrido en preorden. 
	 */
	public List<E> getPreOrder(){
		List<E> r;
		if(isEmpty()){
			r = Lists2.newList();
		} else if(isLeaf()){
			r = Lists2.newList(this.label);
		} else {
			r = Lists2.newList(this.label);
			r.addAll(elements.stream()
					.map(x->x.getPreOrder())
					.reduce(Lists2.newList(),Lists2::concat));
		}
		return r;
	}
	
	
	
	/**
	 * @return Una lista con el recorrido en postorden
	 */
	public List<E> getPostOrder(){
		List<E> r;
		if(isEmpty()){
			r = Lists2.newList();
		} else if(isLeaf()){
			r = Lists2.newList(this.label);
		} else {
			r = elements.stream()
					.map(x->x.getPostOrder())
					.reduce(Lists2.newList(),Lists2::concat);
			r.add(label);
		}
		return r;
	}
	
	/**
	 * @pos La etiqueta se insertará en al posición min(k,nh). Si k = 0 resulta el recorrido en preorden y si 
	 * k &ge; nh en postorden.
	 * @param k Posición de inserción de la etiqueta
	 * @return Una lista con el recorrido en inorden. 
	 */
	public List<E> getInOrder(int k){
		List<E> r;
		if(isEmpty()){
			r = Lists2.newList();
		} else if(isLeaf()){
			r = Lists2.newList(this.label);
		} else {
			List<Tree<E>> nElements = Lists2.newList(elements);
			int nk = Math.min(k, elements.size());
			nElements.add(nk,Tree.leaf(label));
			return nElements.stream()
					.map(x->x.getPostOrder())
					.reduce(Lists2.newList(),Lists2::concat);
			
		}
		return r;
	}
	/**
	 * @return Una lista con los árboles por niveles. Versión iterativa
	 */
	public List<Tree<E>> getByLevel(){
		List<Tree<E>> r = Lists2.newList(this);
		List<Tree<E>> level = Lists2.newList(this);		
		while(!level.isEmpty()){
			level = getNextLevel(level);
			r.addAll(level);
		}
		return r;
	}
	
	/**
	 * @return Una lista con las etiquetas por niveles. Versión iterativa
	 */
	public List<E> getLabelByLevel(){
		return getByLevel().stream()
				.filter(x->!x.isEmpty())
				.map(x->x.getLabel())
				.collect(Collectors.toList());
	}
	
	/**
	 * @param level Los arboles de un nivel dado
	 * @return Los arboles del siguiente nivel
	 */
	public List<Tree<E>> getNextLevel(List<Tree<E>> level) {
		List<Tree<E>> nLevel;
		nLevel = new ArrayList<>();
		for (Tree<E> t : level) {
			if(!(t.isEmpty() || t.isLeaf())){ 
				nLevel.addAll(t.elements);
			}
		}
		return nLevel;
	}
	
	/**
	 * @param n Un entero
	 * @return Los arboles del nivel n
	 */
	public List<Tree<E>> getLevel(Integer n) {
		Integer i =0;
		List<Tree<E>> level = Arrays.asList(this);
		while(i<n){
			level = level.stream().flatMap(x->x.elements.stream()).collect(Collectors.toList());
			i++;
		}
		return level;
	}
	
	/**
	 * @param root La raiz del árbol dónde t es un subarbol
	 * @return La profundidad de t en root o -1 si no está
	 */
	public int getDepth(Tree<E> root){
		List<Tree<E>> level = Lists2.newList(this);
		int n = 0;		
		while(!level.isEmpty()){
			if(level.stream().anyMatch(x->x==this)){
				break;
			}
			level = getNextLevel(level);
			n++;
		}
		return n;
	}
	
	
	public String toString(){		
		String r;
		if(isEmpty()){
			r ="()";
		}else if(isLeaf()){
			r = label.toString()+"()";
		}else {
			r = label.toString()+
					 elements.stream()
					.map(x->x.toString())
					.collect(Collectors.joining(",", "(", ")"));
		}
		return r;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	public static void main(String[] args) {
		Tree<Integer> t1 = Tree.empty();
		Tree<Integer> t2 = Tree.leaf(2);
		Tree<Integer> t3 = Tree.leaf(3);
		Tree<Integer> t4 = Tree.leaf(4);
		Tree<Integer> t5 = Tree.nary(27,t1,t2,t3,t4);
		Tree<Integer> t6 = Tree.nary(39, t2,t5);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t6);
		String ex = "39(2(),27((),2(),3(),4()))";
		Tree<String> t7 = Tree.tree(ex);
		System.out.println(t7);
		System.out.println(Lists2.reverse(Lists2.newList(1,2,3,4,5,6,7,8,9)));
		Tree<String> t8 = t7.getReverse();
		System.out.println(t8);
	}
	
}
