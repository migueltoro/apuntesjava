package us.lsi.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Toro
 *
 *<p> Implementación de un árbol binario </p>
 *
 * @param <T> El tipo de los elementos de las etiquetas del árbol
 */
public class BinaryTree<T> {

	/**
	 * @param label La etiqueta del árbol
	 * @param left El hijo izquierdo
	 * @param right El hijo derecho
	 * @param <T> el tipo de las etiquetas del árbol
	 * @return Un árbol construido con esos elementos
	 */
	public static <T> BinaryTree<T> create(T label, BinaryTree<T> left,
			BinaryTree<T> right) {
		return new BinaryTree<T>(label, left, right);
	}

	/**
	 * @param label La etiqueta del árbol
	 * @param <T> el tipo de las etiquetas del árbol
	 * @return Un árbol hoja sin hijos
	 */
	public static <T> BinaryTree<T> create(T label) {
		return new BinaryTree<T>(label);
	}

	/**
	 * @param <T> el tipo de las etiquetas del árbol
	 * @return Un árbol vacío
	 */
	public static <T> BinaryTree<T> create() {
		return new BinaryTree<T>();
	}

	private T label;
	private BinaryTree<T> left;
	private BinaryTree<T> right;
	public static boolean iterativo = true;
	
	private BinaryTree(T label, BinaryTree<T> left, BinaryTree<T> right) {
		super();
		this.label = label;
		this.left = left;
		this.right = right;
	}
	
	private BinaryTree(T label) {
		super();
		this.label = label;
		this.left = null;
		this.right = null;
	}
	
	private BinaryTree() {
		super();
		this.label = null;
		this.left = null;
		this.right = null;
	}	
	
	/**
	 * @return La etiqueta del árbol o null si es vacío
	 */
	public T getLabel() {
		return label;
	}

	/**
	 * @param label La nueva etiqueta del árbol
	 */
	public void setLabel(T label) {
		this.label = label;
	}

	/**
	 * @return El hijo izquierdo
	 */
	public BinaryTree<T> getLeft() {
		return left;
	}

	/**
	 * @param left El nuevo hijo izquierdo
	 */
	public void setLeft(BinaryTree<T> left) {
		this.left = left;
	}

	/**
	 * @return El hijo derecho
	 */
	public BinaryTree<T> getRight() {
		return right;
	}

	/**
	 * @param right El nuevo hijo derecho
	 */
	public void setRight(BinaryTree<T> right) {
		this.right = right;
	}

	/**
	 * @return Verdadero si el árbol es vacío
	 */
	public boolean isEmpty() {
		return label == null;
	}
	
	/**
	 * @return Verdadero si el árbol es una hoja. Es decir vacío o sin hijos
	 */
	public boolean isLeaf(){
		return isEmpty() || getNumChildren() == 0;
	}
	
	/**
	 * @return El número de etiquetas del árbol
	 */
	public int size(){
		int r;
		if(this.isEmpty()){
			r = 0;
		}else {
			int sizq = this.left!=null?this.left.size():0;
			int sder = this.right!=null?this.right.size():0;
			r = sizq+ sder +1;
		}
		return r;
	}
	
	/**
	 * @return El número de hijos del árbol
	 */
	public int getNumChildren() {
		int r;
		if(this.left==null && this.right==null){
			r = 0;
		}else if(this.left==null && this.right!=null){
			r = 1;
		}else if(this.left!=null && this.right==null) {
			r = 1;
		} else {
			r = 2;
		}
		return r;
	}
	
	
	/**
	 * @return La altura del árbol
	 */
	public int getHeight(){
		int r;
		if(this.isEmpty()){
			r = -1;
		}else {
			int hizq = this.left!=null?this.left.getHeight():-1;
			int hder = this.right!=null?this.right.getHeight():-1;
			r = Math.max(hizq,hder) +1;
		}
		return r;
	}
	
	/**
	 * @pre this es un subarbol de root
	 * @param root La raiz del árbol dónde this es un subarbol
	 * @return La profundidad de this en root
	 */
	public int getDepth(BinaryTree<T> root){
		return root.getHeight()-this.getHeight();
	}
	
	/**
	 * @return Una copia en prod¡fundidad del árbol
	 */
	public BinaryTree<T> getCopy() {
		BinaryTree<T> r;
		if(this.isEmpty()){
			r = BinaryTree.create();
		} else if(this.isLeaf()){
			r = BinaryTree.create(this.label);
		} else {
			BinaryTree<T> cizq = this.left!=null?this.left.getCopy():null;
			BinaryTree<T> cder = this.right!=null?this.right.getCopy():null;
			r = BinaryTree.create(this.label,cizq, cder);
		}
		return r;
	}
	
	/**
	 * @return Un árbol que es la imagen especular de this
	 */
	public BinaryTree<T> getReflejado() {
		BinaryTree<T> r;
		if(this.isEmpty()){
			r = BinaryTree.create();
		} else if(this.isLeaf()){
			r = BinaryTree.create(this.label);
		} else {
			BinaryTree<T> cizq = this.left!=null?this.left.getReflejado():null;
			BinaryTree<T> cder = this.right!=null?this.right.getReflejado():null;
			r = BinaryTree.create(this.label,cder, cizq);
		}
		return r;
	}
	
	/**
	 * @return Una lista con el recorrido en preorden
	 */
	public List<T> getPreOrder(){
		List<T> ls; 
		if (BinaryTree.iterativo) {
			ls = BinaryTree.getPreOrderIter(this);
		} else {
			ls = BinaryTree.getPreOrderRecur(this);
		}	
		return ls;
	}
	
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido en preorden. Versión iterativa
	 */
	public static <E> List<E> getPreOrderIter(BinaryTree<E> t){
		List<E> ls = new ArrayList<>();
		Pila<BinaryTree<E>> p = Pila.create();
		if(!t.isEmpty()) p.add(t);
		while(!p.isEmpty()){
			BinaryTree<E> a = p.remove();
			ls.add(a.getLabel());			
			if(a.right!=null) p.add(a.right);
			if(a.left!=null) p.add(a.left);
		}
		return ls;
	}
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido en preorden. Versión recursiva
	 */
	public static <E> List<E> getPreOrderRecur(BinaryTree<E> t){
		List<E> r; 
		List<E> emptyList = new ArrayList<>();
		if(t.isEmpty()){
			r = new ArrayList<>();
		} else if(t.isLeaf()){
			r = new ArrayList<>();
			r.add(t.getLabel());
		} else {
			List<E> izq = t.left!=null?BinaryTree.getPreOrderRecur(t.left):emptyList;
			List<E> der = t.right!=null?BinaryTree.getPreOrderRecur(t.right):emptyList;
			r = new ArrayList<>();
			r.add(t.getLabel());
			r.addAll(izq);
			r.addAll(der);
		}
		return r;
	}
	/**
	 * 
	 * @return Una lista con el recorrido en postorden.
	 */
	public List<T> getPostOrder(){
		List<T> ls; 
		if (BinaryTree.iterativo) {
			ls = BinaryTree.getPostOrderIter(this);
		} else {
			ls = BinaryTree.getPostOrderRecur(this);
		}	
		return ls;
	}
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido en postorden. Versión iterativa
	 */
	public static <E> List<E> getPostOrderIter(BinaryTree<E> t){
		List<E> ls = new ArrayList<>();
		Pila<Entry<BinaryTree<E>,Boolean>> p = Pila.create();
		if(!t.isEmpty()) p.add(Entry.create(t, false));
		while(!p.isEmpty()){
			Entry<BinaryTree<E>,Boolean> a = p.peek();
			if (a.value) {
				a = p.remove();
				ls.add(a.key.getLabel());				
			} else {
				a.value = true;				
				if (a.key.right != null) p.add(Entry.create(a.key.right,false));
				if (a.key.left != null) p.add(Entry.create(a.key.left,false));
			}
		}
		return ls;
	}
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido en postorden. Versión recursiva
	 */
	public static <E> List<E> getPostOrderRecur(BinaryTree<E> t){
		List<E> r; 
		List<E> emptyList = new ArrayList<>();
		if(t.isEmpty()){
			r = new ArrayList<>();
		} else if(t.isLeaf()){
			r = new ArrayList<>();
			r.add(t.getLabel());
		} else {
			List<E> izq = t.left!=null?BinaryTree.getPostOrderRecur(t.left):emptyList;
			List<E> der = t.right!=null?BinaryTree.getPostOrderRecur(t.right):emptyList;
			r = new ArrayList<>();			
			r.addAll(izq);
			r.addAll(der);
			r.add(t.getLabel());
		}
		return r;
	}
	/**
	 * @return Una lista con el recorrido en inorden. 
	 */
	public List<T> getInOrder(){
		List<T> ls; 
		if (BinaryTree.iterativo) {
			ls = BinaryTree.getInOrderIter(this);
		} else {
			ls = BinaryTree.getInOrderRecur(this);
		}	
		return ls;
	}
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido en inorden. Versión iterativa
	 */
	public static <E> List<E> getInOrderIter(BinaryTree<E> t){
		List<E> ls = new ArrayList<>();
		Pila<Entry<BinaryTree<E>,Boolean>> p = Pila.create();
		if(!t.isEmpty()) p.add(Entry.create(t, false));
		while(!p.isEmpty()){
			Entry<BinaryTree<E>,Boolean> a = p.remove();
			if (a.value) {
				ls.add(a.key.getLabel());				
			} else {
				a.value = true;
				if (a.key.right != null) p.add(Entry.create(a.key.right,false));
				p.add(a);
				if (a.key.left != null) p.add(Entry.create(a.key.left,false));				
			}
		}
		return ls;
	}
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido en inorden. Versión recursiva
	 */
	public static <E> List<E> getInOrderRecur(BinaryTree<E> t){
		List<E> r; 
		List<E> emptyList = new ArrayList<>();
		if(t.isEmpty()){
			r = new ArrayList<>();
		} else if(t.isLeaf()){
			r = new ArrayList<>();
			r.add(t.getLabel());
		} else {
			List<E> izq = t.left!=null?BinaryTree.getInOrderRecur(t.left):emptyList;
			List<E> der = t.right!=null?BinaryTree.getInOrderRecur(t.right):emptyList;
			r = new ArrayList<>();			
			r.addAll(izq);
			r.add(t.getLabel());
			r.addAll(der);			
		}
		return r;
	}
	
	/**
	 * @return Una lista con el recorrido por niveles. 
	 */
	public List<T> getByLevel(){
		return BinaryTree.getByLevel(this);
	}
	/**
	 * @param t Un árbol
	 * @param <E> el tipo de las etiquetas del árbol
	 * @return Una lista con el recorrido por niveles. Versión iterativa
	 */
	public static <E> List<E> getByLevel(BinaryTree<E> t){
		List<E> ls = new ArrayList<>();
		Cola<BinaryTree<E>> p = Cola.create();
		if(!t.isEmpty()) p.add(t);
		while(!p.isEmpty()){
			BinaryTree<E> a = p.remove();
			ls.add(a.getLabel());
			if(a.left!=null) p.add(a.left);
			if(a.right!=null) p.add(a.right);
		}
		return ls;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BinaryTree))
			return false;
		BinaryTree<?> other = (BinaryTree<?>) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s;
		if(this.isEmpty()){
			s = "()";
		} else if(this.isLeaf()){
			s = this.label.toString();
		} else if(this.left != null && this.right != null){
			s = this.label+"("+this.left+","+this.right+")";
		} else if(this.left == null){
			s = this.label+"("+this.right+")";
		} else {
			s = this.label+"("+this.left+")";
		}	
		return  s;
	}
	
}
