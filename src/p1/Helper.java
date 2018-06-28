package p1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Helper {
	private String text;

	public Helper(String text) {
		this.text = text;
	}

	public List<String> getTokens(String pattern) {
		ArrayList<String> tokens = new ArrayList<>();
		Pattern tokenPattern = Pattern.compile(pattern);
		Matcher match = tokenPattern.matcher(text);
		int count = 0;
		while (match.find()) {
			tokens.add(match.group());
			// System.out.print("Match- "+ match.group()+". ");
			++count;
		}
		return tokens;
	}

	public void doAll2(String p1){
		Pattern tokenPattern = Pattern.compile(p1);
		Matcher match = tokenPattern.matcher(text);

		int wordC =0;
		int sentC = 0;
		int syllC = 0;
		String s = null;
		List<Character> sentenceEndChars = Arrays.asList('.','!','?');
		
		while(true){
			if(match.find()){
				s=match.group();
				s= s.toLowerCase();
				boolean isPreviousVowel = false;	//boolean statement used for vowels following each other like "read" or "soon"
				
				for(int i =0; i<s.length();i++){
					char myChar = s.charAt(i);
					if(!isPreviousVowel && 
							(myChar == 'a'
							||(myChar == 'e' && (i < s.length() - 1) && !sentenceEndChars.contains(s.charAt(s.length()-1))) // e is not counted as a syllable in cases like "here" or "here?"
							||myChar == 'i'
							||myChar == 'o'
							||myChar == 'u'
							||myChar == 'y')){
						syllC++;
						isPreviousVowel = true;
					} else {
						isPreviousVowel = false;
					}
				}
				if(s.charAt(s.length()-1)=='.'||s.charAt(s.length()-1)=='!'||s.charAt(s.length()-1)=='?'){
					sentC++;
				}
				
				wordC++;
			}
			else if(!match.find()){
				break;
			}
		}
		//System.out.println("Words: "+wordC+"\nSentences: "+sentC+"\nSyllables: "+syllC);

		
	}

	public abstract int getSyllables();

	public abstract int getNumberOfWords();

	public abstract int getSentences();

	public abstract double getFleschScore();

	public abstract long doAll();
	
	public abstract long do3Loops();

	public String getText() {
		return text;
	}

}
