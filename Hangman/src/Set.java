import java.util.Iterator;

public interface Set<E> extends Iterable<E>{
	
	public boolean add(E k);
	public E remove(E k);
	public int size();
	public boolean isEmpty();
	public Iterator<E> iterator();
	public boolean contains(E k);
	public void addAll(Set<E> otherSet);
	public void removeAll(Set<E> otherSet);
	public void retainAll(Set<E> otherSet);

}
