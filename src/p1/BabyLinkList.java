package p1;

public class BabyLinkList {
	private BabyLink first;
	private BabyLink last;
	private int count;
	
	public BabyLinkList(){
		first = null;
		last = null;
		count=0;
	}
	public boolean isEmpty(){
		return first == null;
	}
	public void displayBabyLink(){
		BabyLink theLink = first;
		while(theLink != null){
			theLink.displayBabyLink();
			//System.out.println("Next Link ->"+ theLink.next);
			theLink = theLink.next;
			System.out.println();
		}
	}
	public void displayData(){
		BabyLink bl = first;
		System.out.println("keyword "+first+" following words ");
		while(bl.next != null){
			bl = bl.next;
			System.out.print(bl.word+" ");
		}
	}
	public int items(){
		return count;
	}
	public void insertFirstLink(String word){
		BabyLink newLink = new BabyLink(word);
		newLink.next = first;
		first = newLink;
		count++;
	}
	public void insertLast(String word){
		BabyLink newbL = new BabyLink(word);
		if(isEmpty()){
			first = newbL;
		}else{
			last.next = newbL;
		}
		last = newbL;
		count++;
	}
	public String getRandomWord(){
		int num = (int)(Math.random()*count);
		BabyLink current = first;
		for(int i =0; i< num; i++){
			current = current.next;
		}
		return current.word;
	}
	

}

