package p1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Readability extends Helper{

	public Readability(String text) {
		super(text);
	}

	@Override
	public int getSyllables() {
		return getTokens("[AEIOUYaiouy]+|e[A-Za-z]").size();
	}

	@Override
	public int getNumberOfWords() {
		return getTokens("[A-Za-z]+").size();
	}

	@Override
	public int getSentences() {
		return getTokens("[.?!]").size();
	}

	@Override
	public double getFleschScore() {
		double words = getNumberOfWords();
		double sentences = getSentences();
		double syl = getSyllables();
		if(getSentences()==0 || getNumberOfWords()==0){
			System.out.println("Cant calculate flesch score,cant divide by 0");
		}
		double score = 206.835 - 1.015*(words/sentences) - 84.6*(syl/words);
		return score;
	}

	@Override
	public long doAll() {
		long time = System.currentTimeMillis();
		doAll2("[A-Za-z.!?]+");
		long end = System.currentTimeMillis();
		long result = end-time;
		return result;
		
	}

	@Override
	public long do3Loops() {
		long time = System.currentTimeMillis();
		getNumberOfWords();
		getSyllables();
		getSentences();
		long end = System.currentTimeMillis();
		
		long result = end-time;
		return result;
		
	}

}
