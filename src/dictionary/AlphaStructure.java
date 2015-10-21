package dictionary;


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
