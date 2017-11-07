

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class GeneralTree<E> implements Tree<E> {
	private int size;
	private Node<E> root;
	private ArrayList<Position<E>> children;
	
	public GeneralTree() {
		size = 0;
		root = null;
		children = new ArrayList<Position<E>>();
	}
	
	public int size() {
		return size;
	}
	

	
	public Position<E> root() {
		return root;
	}
	
	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private ArrayList<Node<E>> children;

		
		
		 public Node(E e, Node<E> above) {
		      element = e;
		      parent = above;
		      children = new ArrayList<Node<E>>();
		    }
		 
		 public E getValue() { 
			 return element; 
			 }
		 
		
		 public Node<E> getParent() { 
			 return parent; 
			 }
		 public ArrayList<Node<E>> getChildren() {
			 return children; 
			 }
		 public void setElement(E e) { 
			 element = e; 
			 }
		 public void setParent(Node<E> parentNode) { 
		     parent = parentNode; 
		     }
		 public void setChildren(Node<E> childrenNode) { 
		     children.add(childrenNode); 
		     }
	
	}	 
	
		 protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
			    if (!(p instanceof Node))
			      throw new IllegalArgumentException("Not valid position type");
			    Node<E> node = (Node<E>) p;       
			    if (node.getParent() == node)     
			      throw new IllegalArgumentException("p is no longer in the tree");
			    return node;
			  }
			
	public Position<E> addRoot(E e) throws IllegalStateException{
			if(!isEmpty()) {
				throw new IllegalStateException("Tree is not empty");
			}
			root = new Node<E>(e, null);
			size = 1;
			return root;
	}
	
	public Position<E> addChild(Position<E> p, E e) {
		Node<E> parent = validate(p);
		Node<E> child = new Node<E>(e, parent);
		parent.setChildren(child);
		size++;
		return child;
		
	}
	
	public E remove(Position<E> p) {
		Node<E> node = validate(p);
		if(!isExternal(p)) {
			throw new IllegalArgumentException("This node has children and it cannot be removed");
		}
		Node<E> theParent = node.getParent();
		theParent.children.remove(node);
		size--;
		E theElement = node.getValue();
		return theElement;
	}


	
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		Position<E> parentP = node.getParent();
		if(parentP == null) {
			throw new IllegalArgumentException("This node has no parent");
		}
		else {
			return parentP;
		}
	}
		public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
			Node<E> nodes = validate(p);
			ArrayList<Position<E>> ListOfChildren = new ArrayList<Position<E>>();
			for (int i = 0; i < nodes.children.size(); i++) {
				ListOfChildren.add(nodes.children.get(i));
			}
			return ListOfChildren;
		}

		public int numChildren(Position<E> p) throws IllegalArgumentException {
			return children.size();
		}

		public boolean isInternal(Position<E> p) throws IllegalArgumentException {
			return children.size() > 0;
		}

		public boolean isExternal(Position<E> p) throws IllegalArgumentException {
			return children.size() == 0;
		}

		@Override
		public boolean isRoot(Position<E> p) throws IllegalArgumentException {
			return parent(p) == root;
		}

		public boolean isEmpty() {
			return size == 0;
		}
		
		  private class ElementIterator implements Iterator<E> {
			    Iterator<Position<E>> posIterator = positions().iterator();
			    public boolean hasNext() { return posIterator.hasNext(); }
			    public E next() { return posIterator.next().getValue(); }
			    public void remove() { posIterator.remove(); }
			  }

		public Iterator<E> iterator() {
			return new ElementIterator();
		}
		
		 public void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
			    snapshot.add(p);                       
			    for (Position<E> c : children(p))
			      preorderSubtree(c, snapshot);
			  }
		
		  public Iterable<Position<E>> preorder() {
			    List<Position<E>> snapshot = new ArrayList<>();
			    if (!isEmpty())
			      preorderSubtree(root(), snapshot);   
			    return snapshot;
		  }
		  
		  

		public Iterable<Position<E>> positions() {
			return preorder();
		}
		
		  public int depth(Position<E> p) throws IllegalArgumentException {
			    if (isRoot(p))
			      return 0;
			    else
			      return 1 + depth(parent(p));
			  }
		
		
		
		
	

}
