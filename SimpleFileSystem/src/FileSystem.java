import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FileSystem implements Serializable {
	private GeneralTree<FileElement> tree;
	private Position<FileElement> currentP;

	public FileSystem() {
		tree = new GeneralTree<>();
		tree.addRoot(new FileElement("", true));
		currentP = tree.root();
	}

	public void cd(String directoryName) {
		try {
			if (directoryName.equals("..")) {
				if (currentP.equals(tree.root())) {
					throw new IllegalStateException();
				} else {
					currentP = tree.parent(currentP);
				}
			} else if (findChild(directoryName) != null) {
				currentP = findChild(directoryName);
			} else {
				throw new IllegalStateException();
			}
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	public void ls() {
		for (Position<FileElement> f : tree.children(currentP)) {
			System.out.println(f.getValue().getFileName());
		}
	}
	
	public void checkMakeFile(String fileName) {
		if(findChild(fileName) != null) {
			throw new IllegalStateException();
		}
	}

	public void mkfile(String filename) throws IllegalStateException {
		try {
			if (findChild(filename) == null) {
				FileElement newFile = new FileElement(filename, false);
				tree.addChild(currentP, newFile);
			} else {
				throw new IllegalStateException("File already exists");
			}
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

	}

	public void mkdir(String directoryName) throws IllegalStateException {
		try {

			if (findChild(directoryName) == null) {
				FileElement newFile = new FileElement(directoryName, true);
				tree.addChild(currentP, newFile);
			} else {
				throw new IllegalStateException("File already exists");
			}
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	public void pwd() {
		ArrayStack<String> s = new ArrayStack<>(16);
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		if(currentP == tree.root()) {
			System.out.println("/" + tree.root().getValue().getFileName());
		}
		Position<FileElement> tempP = currentP;
		while(tempP != tree.root()) {

			s.push(tempP.getValue().getFileName());
			tempP = tree.parent(tempP);
			counter++;
		}
		s.push(tree.root().getValue().getFileName());
		for(int i =0; i<= counter; i++) {
			sb.append('/');
			sb.append(s.pop());
		}

		System.out.println(sb);

	}

	public void rm(String filename) {
		try {
			if (findChild(filename) != null) {
				Position<FileElement> newCurrentP = findChild(filename);
				if (!newCurrentP.getValue().getIsDirectory()) {
					tree.remove(newCurrentP);
				} else {
					throw new IllegalStateException("Not Allowed.  It is a directory");
				}
			} else {
				throw new IllegalStateException("File doesn't exist");
			}
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	public void rmdir(String directoryName) {
		try {
			if (findChild(directoryName) != null) {
				Position<FileElement> newCurrentP = findChild(directoryName);
				if (!newCurrentP.getValue().getIsDirectory()) {
					if (tree.children(newCurrentP) != null) {
						tree.remove(newCurrentP);
					}

					else {
						throw new IllegalStateException("Directory is not empty");
					}
				} else {
					throw new IllegalStateException("Error! It is a directory");
				}
			} else {
				throw new IllegalStateException("File doesn't exist");
			}
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void tree() {

	prettyPrint(currentP, 0);

	}
	
	private void prettyPrint(Position<FileElement> r, int spaces)
	{
		//Process root
		for(int i=0; i < spaces; i++)
		{
			System.out.print(" ");
		}
		System.out.println(r.getValue().getFileName());
		Iterable<Position<FileElement>> it = tree.children(r);
		
		for(Position<FileElement> e : it) {
			prettyPrint(e, tree.depth(e) + 2);
		}
	}
	public void prettyPrint()
	{
		prettyPrint(currentP, 0);
	}


	private Position<FileElement> findChild(String name) {
		if (tree.children(currentP) == null) {
			return null;
		}
		String theFilename = "";
		for (Position<FileElement> f : tree.children(currentP)) {
			theFilename = f.getValue().getFileName();
			if (theFilename.equals(name)) {
				return f;
			}
		}
		return null;

	}

}
