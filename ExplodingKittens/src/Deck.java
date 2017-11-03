import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	ArrayList<Card> deck;
	private int numPlayers;
	
	public Deck(int numPlayers) {
		this.numPlayers = numPlayers;
		deck = new ArrayList<Card>();
		for(int i = 0; i < 4; i++) {
			deck.add(new Card("Cattermelon"));
			deck.add(new Card("Tacocat"));
			deck.add(new Card("Shuffle"));
			deck.add(new Card("Rainbow-Ralphing Cat"));
			deck.add(new Card("Skip"));
			deck.add(new Card("Hairy Potato Cat"));
			deck.add(new Card("Favor"));
			deck.add(new Card("Beard Cat"));
			deck.add(new Card("Attack"));
			deck.add(new Card("Nope"));
			deck.add(new Card("See the Future"));

		}
		
		deck.add(new Card("Nope"));
		deck.add(new Card("See the Future"));
		
	}
	
	public void add(Card card) {
		deck.add(card);
	}
	
	public void add(int position, Card card) {
		deck.add(position, card);
	}
	
	public void addDefuses() {
		if(numPlayers == 2) {
			deck.add(new Card("Defuse"));
			deck.add(new Card("Defuse"));
		}
		else {
			for(int i = 0; i < 4; i++) {
				deck.add(new Card("Defuse"));

			}
		}
	}
	
	public void addKittens() {
		for(int i = 0; i < numPlayers - 1; i++) {
			deck.add(new Card("Exploding Kitten"));

		}
		
	}
	
	public Card top() {
		return deck.get(0);
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public int size() {
		return deck.size();
	}
	
	public void draw(Hand toHand) {
		toHand.addCard(deck.get(0));
		deck.remove(0);
	}
	
	public ArrayList<Card> topThree() {
		ArrayList<Card> topThree = new ArrayList<>();
		topThree.add(this.deck.get(0));
		topThree.add(this.deck.get(1));
		topThree.add(this.deck.get(2));
		return topThree;

	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public String toString() {
		return deck.toString();
	}
	
	

}
