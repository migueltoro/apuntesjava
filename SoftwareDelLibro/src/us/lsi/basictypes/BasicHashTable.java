package us.lsi.basictypes;



public class BasicHashTable<K, V> {
	public static <K, V> BasicHashTable<K, V> create(int capacity,
			int initialCapacityOfGroups, double loadFactorReference) {
		return new BasicHashTable<K, V>(capacity, initialCapacityOfGroups,
				loadFactorReference);
	}


	public static <K, V> BasicHashTable<K, V> create() {
		return new BasicHashTable<K, V>();
	}


	private int capacity;
	private final int GROWING_FACTOR = 2;
	private int initialCapacityOfGroups;
	private int size;
	private double loadFactorReference;
	private BasicDynamicArray<BasicDynamicArray<Entry<K,V>>> elements;
	
	private BasicHashTable(int capacity, int initialCapacityOfGroups, double loadFactorReference) {
		super();
		this.capacity = capacity;
		this.initialCapacityOfGroups = initialCapacityOfGroups;
		this.loadFactorReference = loadFactorReference;
		initial();
	}

	
	private BasicHashTable() {
		super();
		this.capacity = 10;
		this.initialCapacityOfGroups = 2;
		this.loadFactorReference = 0.75;
		initial();
	}
	
	
	private void initial(){
		elements = BasicDynamicArray.create(capacity);
		for(int i = 0; i < capacity; i++){
			elements.add(BasicDynamicArray.<Entry<K,V>>create(initialCapacityOfGroups));
		}		
	}
	
	private int hash(int a){
		return a%capacity;
	}
	
	private void rehash(int newCapacity){
		BasicDynamicArray<BasicDynamicArray<Entry<K,V>>> oldElements = elements;
		int oldCapacity = capacity;
		capacity = newCapacity;
		initial();
		Entry<K,V> e;
		for(int i = 0; i < oldCapacity; i++){
			for(int j=0; j < oldElements.get(i).size(); j++){
				e = oldElements.get(i).get(j);
				put(e);
			}
		}
	}
	
	private double getLoadFactor(){
		double sd = size;
		return sd/capacity;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	
	
	private Entry<K,V> getEntry(K key){
		int c = hash(key.hashCode());
		Entry<K,V> r = null;
		for(int i = 0; i < elements.get(c).size();i++){
			if(key.equals(elements.get(c).get(i).getKey())){
				r = elements.get(c).get(i);
			}
		}
		return r;
	}
	
	public V get(K key){
		Entry<K,V> e = getEntry(key);
		V r = null;
		if(e != null){
			r = e.getValue();
		}
		return r;
	}
	
	private void put(Entry<K,V> e){
		int c = hash(e.getKey().hashCode()); 
		elements.get(c).add(e);		
		if(getLoadFactor() > loadFactorReference ){
			rehash(capacity*GROWING_FACTOR);
		}
	}
	
	public V put(K key, V value){
		Entry<K,V> e = getEntry(key);
		V r = null;
		if(e == null){
			put(new Entry<K,V>(key,value));
			size++;
		} else {
			r = e.getValue();
			e.setValue(value);
		}
		return r;
	}
	
	public V remove(K key){
		int c = hash(key.hashCode());
		V r = null;
		int  p = -1;
		for(int i = 0; i < elements.get(c).size();i++){
			if(key.equals(elements.get(c).get(i).getKey())){
				r = elements.get(c).get(i).getValue();
				p = i;
			}
		}
		if(p >= 0){
			elements.get(c).remove(p);
			size--;
		}
		return r;
	}
	
	public String toString(){
		String s = "{";
		boolean prim = true;
		for(int i=0; i < capacity; i++){
			for(int j=0;j< elements.get(i).size(); j++){				
				if(prim){
					prim = false;
					s = s+elements.get(i).get(j);
				}else{
					s = s+","+elements.get(i).get(j);
				}
			}
		}
		s = s+"}";
		return s;
	}
	
	
	public class Entry<K1,V1> {
		private K1 key;
		private V1 value;
		public Entry(K1 key, V1 value) {
			super();
			this.key = key;
			this.value = value;
		}
		public K1 getKey() {
			return key;
		}
		public void setKey(K1 key) {
			this.key = key;
		}
		public V1 getValue() {
			return value;
		}
		public void setValue(V1 value) {
			this.value = value;
		}
		public String toString(){
			return "("+key+","+value+")";
		}
	}
}
