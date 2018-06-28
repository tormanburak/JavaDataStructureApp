package p1;

public class HashTable {
		
	WordList[] theArray;
	int arraySize;


	
	public int stringhashFunction(String wordToHash){
		int hashKeyValue =0;
		for(int i =0; i<wordToHash.length();i++){
			
			int charCode = wordToHash.charAt(i)-96;
			int hKVTemp = hashKeyValue;
			hashKeyValue = (hashKeyValue * 27 + charCode)% arraySize;
		}
		return hashKeyValue;
	}
	  HashTable(int size){
		arraySize = size;
		theArray = new WordList[size];
		for(int i=0; i<arraySize;i++){
			theArray[i] = new WordList();
		}
	}	
	public void insert(Word newWord){
		String wordToHash = newWord.theWord;
		int hashKey = stringhashFunction(wordToHash);
		theArray[hashKey].insert(newWord, hashKey);
			
	}
	public Word find(String wordToFind){
		Word theWord = null;
		try{
		int hashKey = stringhashFunction(wordToFind);
		//if (hashKey > 0 &&  hashKey < theArray.length && theArray[hashKey] != null ) {
			theWord = theArray[hashKey].find(hashKey,wordToFind);
	//	}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		return theWord;
	}
	public void addTheArray(String [] elementsToAdd){
		for(int i=0; i< elementsToAdd.length;i++){
			String word = elementsToAdd[i];
			
			Word newWord = new Word(word);
			insert(newWord);
		}
		
	}
	public void displayTheArray(){
		for(int i =0; i<arraySize;i++){
			System.out.println("theArray index "+i);
			theArray[i].displayWordList();
		}
	}
}
