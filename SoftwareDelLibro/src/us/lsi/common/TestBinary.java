package us.lsi.common;

public class TestBinary {


	public static void main(String[] args) {
		BinaryTree<String> b1 = BinaryTree.create("b", 
				BinaryTree.create("0"), BinaryTree.create("1"));
		
		BinaryTree<String> b2 = BinaryTree.create("c", 
				BinaryTree.create("2"), BinaryTree.create("3"));
		BinaryTree<String> b3 = BinaryTree.create("a",b1,b2);
		BinaryTree<String> b4 = BinaryTree.create("d",b1,b3);
		System.out.println(b4);
		System.out.println(b4.getReflejado());
		System.out.println("_____");
		System.out.println(b4.getPreOrder());
		System.out.println(b4.getInOrder());
		System.out.println(b4.getPostOrder());
		System.out.println(b4.getByLevel());		
		BinaryTree.iterativo = false;
		System.out.println("_____");
		System.out.println(b4.getPreOrder());
		System.out.println(b4.getInOrder());
		System.out.println(b4.getPostOrder());
		System.out.println(b4.getByLevel());
		System.out.println("_____");
		System.out.println(b4.getCopy());
		System.out.println(b4.equals(b4.getCopy())?"true":"false");
		System.out.println(b4.getHeight());
		System.out.println(BinaryTree.create("3").getDepth(b4));		
	}

}

