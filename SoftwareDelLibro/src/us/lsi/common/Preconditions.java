package us.lsi.common;

public class Preconditions {

	/**
	 * Checks that the boolean is true. Use for validating arguments to methods.
	 * @param condition A condition
	 */
	public static void checkArgument(boolean condition){
		if(!condition){
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Checks that the boolean is true. Use for validating arguments to methods.
	 * @param message A message
	 * @param condition A condition
	 */
	public static void checkArgument(boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks some state of the object, not dependent on the method arguments.  
	 * @param condition A condition
	 */
	public static void checkState(boolean condition){
		if(!condition){
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Checks some state of the object, not dependent on the method arguments. 
	 * @param message 
	 * @param condition A condition
	 */
	public static void checkState(boolean condition, String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * Checks that the value is not null. 
	 * Returns the value directly, so you can use checkNotNull(value) inline.
	 * @return reference
	 */
	public static <T> T checkNotNull(T reference){
		if(reference == null){
			throw new NullPointerException(String.format("Es nulo %s", reference));
		}
		return reference;
	}
		
	/**
	 * Checks that index is a valid element index into a list, string, or array with the specified size. 
	 * An element index may range from 0 inclusive to size exclusive. 
	 * You don't pass the list, string, or array directly; you just pass its size. 
	 * @param index Un índice 
	 * @param size 
	 * @return Index.
	 */
	public static int checkElementIndex(int index, int size){
		if(!(index>=0 && index<size)){
			throw new IndexOutOfBoundsException(String.format("Index = %d, size %d", index,size));
		}
		return index;
	}
	
	/**
	 * Checks that index is a valid position index into a list, string, or array with the specified size. 
	 * A position index may range from 0 inclusive to size inclusive. 
	 * You don't pass the list, string, or array directly; you just pass its size. Returns index.
	 * @param index
	 * @param size
	 * @return Index.
	 */
	public static int checkPositionIndex(int index, int size){
		if(!(index>=0 && index<=size)){
			throw new IndexOutOfBoundsException(String.format("Index = %d, size %d", index,size));
		}
		return index;
	}
	
	
}
