package us.lsi.basictypes;

import java.util.Arrays;
import java.util.List;

public class BinaryTree<E> {
	
	public static <E> BinaryTree<E> create() {
		return new BinaryTree<E>();
	}

	public static <E> BinaryTree<E> create(E label) {
		return new BinaryTree<E>(label);
	}

	public static <E> BinaryTree<E> create(E label, BinaryTree<E> left,
			BinaryTree<E> right) {
		return new BinaryTree<E>(label, left, right);
	}

	private Tree<E> tree;
	
	protected BinaryTree() {
		this.tree = Tree.create();
	}
	
	
	protected BinaryTree(E label){
		this.tree = Tree.create(label);
	}
		
	protected BinaryTree(E label, BinaryTree<E> left, BinaryTree<E> right) {
		List<Tree<E>> ls = Arrays.asList(left.tree,right.tree);
		this.tree = Tree.create(label, ls);
	}

	private BinaryTree(Tree<E> tree) {
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
