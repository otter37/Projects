
public class Card implements Comparable<Card>{
	
	private String type;
	
	
	public Card(String type) {
		this.type = type;
	}
	
	public String getCard() {
		return type;
	}
	
	public String toString() {
		return type;
	}
	
    public int compareTo(Card compareCard) {
        String compareType=((Card)compareCard).getCard();
        /* For Ascending order*/
        return this.type.compareTo(compareType);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	

}
