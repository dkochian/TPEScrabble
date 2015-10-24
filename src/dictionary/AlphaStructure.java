package dictionary;

import java.util.Arrays;

/**
 * Class used to store alphabetic references, used for storing the amount 
 * of letters or the values of each letter, by accessing the array with 
 * the char value there is no need to travel through the whole array. 
 * O(1)
 */
public class AlphaStructure {

	private Integer[] array;
	
	public AlphaStructure(Integer[] array){
		this.array=array;
	}
	
	public int get(Character c){
		System.out.println(c.toString());
		return array[c - 'A'];
	}
	
	public void put(Character c, Integer val){
		array[c - 'A'] = val;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(array);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlphaStructure other = (AlphaStructure) obj;
		if (!Arrays.equals(array, other.array))
			return false;
		return true;
	}
	

}
