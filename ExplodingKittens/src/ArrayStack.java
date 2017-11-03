
public class ArrayStack<E> implements Stack<E> {

	private E[] theArray;
	private int size;

	public ArrayStack(int capacity) {
		theArray = (E[]) new Object[capacity];
		size = 0;
	}

	public void push(E v) {

		if (size == theArray.length) {
			throw new IllegalStateException("Stack is full");
		}
		theArray[size] = v;
		size++;

	}

	public E pop() {
		if (isEmpty()) {
			throw new IllegalStateException("Stack is empty");
		}
		E temp = theArray[size - 1];
		theArray[size - 1] = null;
		size--;
		return temp;
	}

	public E top() {
		if (isEmpty()) {
			throw new IllegalStateException("Stack is empty");
		}
		return theArray[size - 1];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

}
