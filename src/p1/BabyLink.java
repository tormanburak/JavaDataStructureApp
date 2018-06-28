package p1;

public class BabyLink {
	public String word;
	public BabyLink next;
	
	public BabyLink(String word){
		this.word = word;
	}
	public void displayBabyLink(){
		System.out.println("Word "+ word);
	}
	@Override
	public String toString() {
		return word;
	}
	
}
