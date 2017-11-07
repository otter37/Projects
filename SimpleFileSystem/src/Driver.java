

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws IllegalStateException{
		FileSystem FS1 = new FileSystem();

			String command;
			Scanner keyboard = new Scanner(System.in);
			do{
				System.out.println("Enter a command. Enter \"quit\" to quit: ");

				command = keyboard.next();
				
				switch(command) {
					case "cd":
						FS1.cd(keyboard.next());
						break;
					case "ls":
						FS1.ls();
						break;
					case "mkfile":
						FS1.mkfile(keyboard.next());
						break;	
					case "touch":
						FS1.mkfile(keyboard.next());
						break;
					case "mkdir":
						FS1.mkdir(keyboard.next());
						break;
					case "pwd":
						FS1.pwd();
						break;
					case "rm":
						FS1.rm(keyboard.next());
						break;
					case "rmdir":
						FS1.rmdir(keyboard.next());
						break;
					case "tree":
						FS1.tree();
						break;
					case "quit":
						System.exit(0);
						break;
					default:
						System.out.println("Invalid command");
						break;
				}
	} while(!command.equalsIgnoreCase("quit"));
		
		try {

	         // create a new file with an ObjectOutputStream
	         FileOutputStream out = new FileOutputStream("fs.data");
	         ObjectOutputStream oout = new ObjectOutputStream(out);
	         
	         
	 		oout.writeObject(FS1);
			keyboard.close();

	      } catch (Exception ex) {
	          ex.printStackTrace();
	       }
		}
	}


