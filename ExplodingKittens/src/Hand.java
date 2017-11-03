import java.util.ArrayList;
import java.util.Collections;

public class Hand {
	
	private ArrayList<Card> cards;
	
	public Hand() {
		cards = new ArrayList<Card>();
		cards.add(new Card("Defuse"));
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}

	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public void removeCard(int position) {
		cards.remove(position);
	}
	
	public Card get(String cardName) {
		for(int i = 0; i < cards.size(); i++) {
			if (cards.get(i).toString() == cardName) {
				return cards.get(i);
			}
		}
		return null;
	}
	
	public void sort() {
		Collections.sort(cards);
	}
	
	public Card get(int i) {
		return cards.get(i);
	}
	
	public boolean contains(String cardName) {
		for(Card card : cards) {
			if(card.toString() == cardName) {
				return true;
			}
		}
		return false;
	}
	
	public int size() {
		return cards.size();
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public String toString() {
		String hand ="";
		for(int i=0; i < cards.size(); i++) {
			hand += cards.get(i) + " (" + (i+1) + ")";
			if(i < cards.size() - 1) {
				hand += ", ";
			}
		}
		return hand;
	}
	
	
	
	
}
