package p1;

public class Link {
	public String keyword;
	public BabyLinkList babyLinkList;
	public Link next;
	
	public Link(String newWord){
		keyword = newWord;
		next = null;
	}
	public void createbabyLinkList(){
		babyLinkList = new BabyLinkList();
		//System.out.println("baby link created for : "+keyword);
	}
	public void insertToBabyLinkList(String word){
		babyLinkList.insertLast(word);
	}
	
	public void displayLink(){
		System.out.println("Key word-> "+keyword);
	}
	
	@Override
	public String toString() {
		return keyword;
	}

}
