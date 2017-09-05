package us.lsi.basictypes;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BinaryTree<E> {
	
	public static <E> BinaryTree<E> create() {
		return new BinaryTree<E>();
	}

	public static <E> BinaryTree<E> create(E label) {
		return new BinaryTree<E>(label);
	}

	public static <E> BinaryTree<E> create(E label, BinaryTree<E> left,BinaryTree<E> right) {
		return new BinaryTree<E>(label, left, right);
	}

	public static <E> BinaryTree<E> create(BinaryTree<E> bTree) {
		if(bTree== null) return null;
		return new BinaryTree<E>(bTree.tree);
	}
	
	public static <E> BinaryTree<E> create(List<E> ls) {
		return new BinaryTree<E>(ls);
	}
	
	public static <E> BinaryTree<E> create(String s, Function<String,E> fLabel){
		return new BinaryTree<E>(s, fLabel);
	}
	
	private Tree<E> tree;
	
	protected BinaryTree() {
		this.tree = Tree.create();
	}
		
	protected BinaryTree(E label){
		this.tree = Tree.create(label);
	}
		
	protected BinaryTree(E label, BinaryTree<E> left, BinaryTree<E> right) {
		List<Tree<E>> ls = Arrays.asList(left==null?null:left.tree,right==null?null:right.tree);
		this.tree = Tree.create(label, ls);
	}
	
	protected BinaryTree(List<E> ls){
		this.tree = Tree.create(ls);
	}
			
	private BinaryTree(String s, Function<String, E> fLabel) {
		this.tree = Tree.create(s, fLabel);
	}

	protected BinaryTree(Tree<E> tree) {
		this.tree = tree;
	}
	
	public E getLabel() {
		return tree.getLabel();
	}

	public BinaryTree<E> getLeft() {
		return new BinaryTree<E>(tree.getElement(0));
	}
	
	public BinaryTree<E> getRight() {
		return new BinaryTree<E>(tree.getElement(1));
	}

	@Override
	public String toString() {
		return tree.toString();
	}


	public int hashCode() {
		return tree.hashCode();
	}

	public boolean equals(Object obj) {
		return tree.equals(obj);
	}

	public int size() {
		return tree.size();
	}

	public void toDOT(String file, String titulo) {
		tree.toDOT(file, titulo);
	}

	public boolean isEmpty() {
		return tree.isEmpty();
	}


	public boolean isRoot() {
		return tree.isRoot();
	}


	public boolean isLeaf() {
		return tree.isLeaf();
	}


	public void setLabel(E label) {
		tree.setLabel(label);
	}


	public BinaryTree<E> getParent() {
		return new BinaryTree<E>(tree.getParent());
	}


	public BinaryTree<E> setLeft(BinaryTree<E> left) {
		return new BinaryTree<E>(tree.setElement(0, left.tree));
	}
	
	public BinaryTree<E> setRight(BinaryTree<E> right) {
		return new BinaryTree<E>(tree.setElement(1, right.tree));
	}

	public int getDepth() {
		return tree.getDepth();
	}

	public int getHeight() {
		return tree.getHeight();
	}
	
	
}
