import java.util.ArrayList;

public class Player {
	
	private String name;
	private Hand hand;
	private boolean isCPU;
	
	public Player(String name, boolean isCPU) {
		hand = new Hand();
		this.name = name;
		this.isCPU = isCPU;
		
	}
	
	public String getName() {
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isCPU() {
		return isCPU;
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	public boolean hasDefuse() {
		for(int i = 0; i < hand.size(); i++) {
			if(hand.get(i).getCard() == "Defuse") {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPair() {
		hand.sort();
		for(int i = 0; i < hand.size() -1; i++) {
			for(int j = i+1; j < hand.size(); j++) {
				if((hand.get(i).toString() == hand.get(j).toString()) && hand.get(i).toString() != "Nope" && 
						hand.get(i).toString() != "Attack" && hand.get(i).toString() != "Skip" && hand.get(i).toString() != "Defuse" && hand.get(i).toString() != "See the Future" && hand.get(i).toString() != "Shuffle"){
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<Card> getPair() {
		if(!hasPair()) {
			return null;
		}
		else {
			ArrayList<Card> list = new ArrayList<>();
			hand.sort();
			for(int i = 0; i < hand.size() -1; i++) {
				for(int j = i+1; j < hand.size(); j++) {
					if((hand.get(i).toString() == hand.get(j).toString()) && hand.get(i).toString() != "Nope" && 
							hand.get(i).toString() != "Attack" && hand.get(i).toString() != "Skip" && hand.get(i).toString() != "Defuse" && hand.get(i).toString() != "See the Future" && hand.get(i).toString() != "Shuffle") {
						if(!list.contains(hand.get(i))) {
						list.add(hand.get(i));
						}
					}
				}
			}
			return list;
			
		}
		
	}
	
	public String getWorst() {
		if(hand.contains("Hairy Potato Cat")) {
			return "Hairy Potato Cat";
		}
		else if(hand.contains("Tacocat")) {
			return "Tacocat";
		}
		else if(hand.contains("Rainbow-Ralphing Cat")) {
			return "Rainbow-Ralphing Cat";
		}
		else if(hand.contains("Cattermelon")) {
			return "Cattermelon";
		}
		else if(hand.contains("Beard Cat")) {
			return "Beard Cat";
		}
		else if(hand.contains("Favor")) {
			return "Favor";
		}
		else if(hand.contains("Nope")) {
			return "Nope";
		}
		else if(hand.contains("Skip")) {
			return "Skip";
		}
		else if(hand.contains("Attack")) {
			return "Attack";
		}
		else if(hand.contains("Defuse")) {
			return "Defuse";
		}
		else {
		return null;
		}
	}
	
	public void cpuPlay(Deck deck, Pile pile, ArrayList<Player> players) {
		
	}

}
