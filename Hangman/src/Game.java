import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Game {

	private int letters;
	private int guesses;
	private String theWord;
	private int numGuessed;
	private ArraySet<String> wordsLeft = new ArraySet<>();
	String[] theBoard;

	public Game(int letters, int guesses) {
		this.letters = letters;
		this.guesses = guesses;
		theWord = "";
		numGuessed = 0;
		theBoard = new String[letters];
		for (int i = 0; i < letters; i++) {
			theBoard[i] = "_";
		}
		Scanner sc = null;
		try {
			sc = new Scanner(new File("dictionary.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc.hasNext()) {
			String nextWord = sc.next();
			if (nextWord.length() == letters) {
				this.wordsLeft.add(nextWord);
			}
		}

	}

	public void guess(CharSequence guess) {
		if (theWord == "") {
			ArraySet<String> tempArr = new ArraySet<>();
			for(String s : wordsLeft) {
				tempArr.add(s);
			}
			Iterator<String> it = wordsLeft.iterator();
			int numRemoved = 0;
			while (it.hasNext()) {

				String nextWord = it.next();
				if (nextWord.contains(guess)) {
					it.remove();
					numRemoved++;

				}

			}
			if (numRemoved == tempArr.size()) {
				theWord = tempArr.removeRandom();
				for(int i =0; i < letters; i++) {
					if(theWord.charAt(i) == guess.charAt(0)) {
						theBoard[i] = guess.toString();
					}
				}
				System.out.println("Good guess!");

			}
			else {
				System.out.println("Wrong! Try again.");
			}
		}
		else {
			if(theWord.contains(guess)) {
				for(int i =0; i < letters; i++) {
					if(theWord.charAt(i) == guess.charAt(0)) {
						theBoard[i] = guess.toString();
					}
				}
				System.out.println("Good guess!");
			}
			else {
				System.out.println("Wrong! Try again.");
			}
		}
		numGuessed++;
		if(numGuessed >= guesses && theWord =="") {
			theWord = wordsLeft.removeRandom();
		}
	}

	public void displayBoard() {
		for (int i = 0; i < letters; i++) {
			System.out.print(theBoard[i] + " ");
		}
		System.out.print("\n");

	}
	
	public int getGuesses() { 
		return numGuessed;
	}

	public boolean hasLost() {
		return numGuessed >= guesses;
	}

	public boolean hasWon() {
		for(String s : theBoard) {
			if(s == "_") {
				return false;
			}
		}
		return true;
	}
	public String getWord() {
		return theWord;
	}


}
