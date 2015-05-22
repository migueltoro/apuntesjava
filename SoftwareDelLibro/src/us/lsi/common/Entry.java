package us.lsi.common;

/**
 * @author Miguel Toro
 * 
 * Un tipo para representar pares clave valor
 *
 * @param <K> El tipo de la clave
 * @param <V> El tipo del valor
 */
public class Entry<K, V> {

	public K key;
	public V value;

	public static <K, V> Entry<K, V> create(K key, V value) {
        return new Entry<>(key, value);
    }
	
	private Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "<"+key + ","+ value + ">";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Entry))
			return false;
		Entry<?,?> other = (Entry<?, ?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
