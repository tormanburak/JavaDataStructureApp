package p1;


public class WordList {
	public Word firstWord = null;
	
	public void insert(Word newWord, int hashKey){
		Word previous = null;
		Word current = firstWord;
		
		newWord.key= hashKey;
		
		while(current != null && newWord.key>current.key){
			previous = current;
			current = current.next;
		}
		if(previous == null){
			firstWord = newWord;
		}else{
			previous.next = newWord;
		}
		newWord.next=current;
	}
	public void displayWordList(){
		Word current = firstWord;
		while(current != null){
		System.out.println(current);
		current = current.next;
		}
	}
	public Word find(int hashKey,String wordToFind){
		Word current = firstWord;
		while(current != null && current.key <= hashKey){
			if(current.theWord.equals(wordToFind)){
				return current;
			}else{
				current = current.next;
			}
		}
		return null;
	}
}
