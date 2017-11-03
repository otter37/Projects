
public class Pile {
	
	ArrayStack<Card> pile;
	
	public Pile() {
		this.pile = new ArrayStack<Card>(56);
	}
	
	public void add(Card card) {
		pile.push(card);
	}
	
	public void take(Card card) {
		pile.pop();
	}
	
	public Card top() {
		return pile.top();
	}
	
	public Card second() {
		Card tempCard = pile.pop();
		Card secondCard = pile.top();
		pile.push(tempCard);
		return secondCard;
	
	}
	
	public String toString() {
		return pile.toString();
	}

}
