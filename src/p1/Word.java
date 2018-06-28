package p1;



public class Word {
	public String theWord;
	
	public int key;
	public Word next;
	
	public Word(String theWord){
		this.theWord=theWord;
	}

	@Override
	public String toString() {
		return "Word : "+ theWord;
	}
	
}
