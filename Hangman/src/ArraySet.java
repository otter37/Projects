import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ArraySet<E> implements Set<E>, Iterable<E> {
	
	private ArrayList<E> set;
	
	public ArraySet() {
		set = new ArrayList<>();
	}


	public boolean add(E k) {
		if(set.contains(k)) {
			return false;
		}
		else {
			set.add(k);
			return true;
		}
	}
	
	private E getValue(E k) {
		for(int i = 0; i < set.size();i++) {
			if (set.get(i) == k) {
				return set.get(i);
			}
		}
		return null;
	}

	public E remove(E k) {
		E value = this.getValue(k);
		set.remove(k);
		return value;
		
	}
	
	public E removeRandom() {

		Random rand = new Random();
		int choice = rand.nextInt(set.size());

		E result = set.get(choice);
		set.remove(choice);


		return result;
	}

	public int size() {
		return set.size();
	}

	public boolean isEmpty() {
		return set.size()!=0;
	}

	@Override
	public Iterator<E> iterator() {
		return set.iterator();
	}

	@Override
	public boolean contains(E k) {
		return set.contains(k);
	}

	@Override
	public void addAll(Set<E> otherSet) {
		for(E v: otherSet) {
			this.add(v);
		}
		
	}

	@Override
	public void removeAll(Set<E> otherSet) {
		for(E v: otherSet) {
			this.remove(v);
		}
	}

	@Override
	public void retainAll(Set<E> otherSet) {
		Iterator<E> it = this.iterator();
		while(it.hasNext()) {
			if(!otherSet.contains(it.next())) {
				it.remove();
			}
		}

	}
	
	public String toString() {
		return set.toString();
	}

}
