import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		while(true) { 
			System.out.println("How many letters is the word?");
			int letters = keyboard.nextInt();
			System.out.println("How many guesses do you want?");
			int guesses = keyboard.nextInt();
			Game game  = new Game(letters, guesses);
			game.displayBoard();
			
			while(game.hasLost() == false && game.hasWon() == false) {
				System.out.println("What is your guess?");
				game.guess(keyboard.next());
				game.displayBoard();
			}
			if(game.hasWon() == true) {
				System.out.println("Congratulations! You won in " + game.getGuesses() + " guesses!");
			}
			else {
				System.out.println("Sorry! You lose!");
				System.out.println("The word was " + game.getWord());
			}
			System.out.println("Would you like to play again? Y/N");
			if(keyboard.next().equalsIgnoreCase("N")) {
				System.exit(0);
			}
			
		}


		
	}	

}
