package p1;

public class LinkedList {
	private Link first;
	private Link last;
	private Link current;
	private int n;
	
	public LinkedList(){
		first = null;
		last = null;
		n=0;
	
		
	}
	public boolean isEmpty(){
		return first == null;
	}
	public void displayLink(){
		Link theLink = first;
		while(theLink != null){
			theLink.displayLink();
			System.out.println("Next Link-> "+theLink.next);
			theLink = theLink.next;
			System.out.println();
		}
	}
	
	public Link find(String keyword){
		Link theLink = first;
		while(theLink != null){
			if (theLink.keyword.equals(keyword)) {
				return theLink;
			}
			theLink = theLink.next;
		}
		return null;
	}
	
	
	public void insertFirstLink(String word){
		Link newLink = new Link(word);
		if(find(word)==null){
			newLink.createbabyLinkList();
		}
		newLink.next = first;
		first = newLink;
		n++;
	
	}
	public int items(){
		return n;
	}
	public void insertLast(String keyword){
		Link link = find(keyword);
		if(link == null){
			link = new Link(keyword);
			link.createbabyLinkList();
			if(isEmpty()){
				first = link;
			} else {
				last.next = link;
				current.insertToBabyLinkList(keyword);
				//System.out.println("1current :"+current+" babylink "+keyword);
				//current.babyLinkList.displayBabyLink();
			}
			current = last = link;
			n++;
		} else {
			current.insertToBabyLinkList(keyword);
			current = link;
			//System.out.println("2current :"+current+" babylink "+keyword);
			//current.babyLinkList.displayBabyLink();
		}
	}
}

