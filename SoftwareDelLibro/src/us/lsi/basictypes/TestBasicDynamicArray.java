package us.lsi.basictypes;



public class TestBasicDynamicArray {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BasicDynamicArray<Integer> d = BasicDynamicArray.create();
		for(int i = 0; i < 23; i++){
			d.add(i);
		}
		d.add(23,-1);
//		d.remove(3);
		d.set(2,-3);
		d.set(40,-1);
		d.set(28,-1);
		d.set(30,-1);
		System.out.println("Array = "+d+"=="+d.size());
		
		BasicLinkedList<Integer> d2 = new BasicLinkedList<Integer>();
		for(int i = 0; i < 23; i++){
			d2.add(i);
		}
		d2.add(23, -1);
//		d2.remove(3);
		d2.set(2,-3);		

		System.out.println("Array = "+d2+"=="+d2.size());
		
		BasicDynamicArray<Integer> d3 = BasicDynamicArray.create();
		for(int i = 0; i < 23; i++){
			d3.set(i,2*i);
		}
		System.out.println("Array = "+d3+"=="+d3.size());
		BasicHashTable<Integer,Double> d4 = BasicHashTable.create();
		for(int i=0;i<20;i++){
			d4.put(i, 5.*i);
		}
		double r = d4.put(3, -15.);
		double r2 = d4.remove(6);
		
		System.out.println("Hash = "+d4+"=="+d4.size()+","+r+","+r2);
	}

}
