import java.io.Serializable;

public class FileElement implements Serializable {
	private String filename;
	private boolean isDirectory;
	

	
	public FileElement(String fn, boolean d) {
		filename = fn;
		isDirectory = d;
	}
	
	public String getFileName() {
		return filename;
	}
	
	public boolean getIsDirectory() {
		return isDirectory;
	}
	
	public String toString() {
		return filename;
	}
}
