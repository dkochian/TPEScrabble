package dictionary;

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
		return array[c - 'A'];
	}
	
	public void put(Character c, Integer val){
		array[c - 'A'] = val;
	}
	

}
