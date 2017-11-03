import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class GameDriver {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Random rand = new Random();
		int decision = 0;
		int playerToTakeFromIndex;
		int kittyPosition;

		while (true) {
			Player playerToRemove = null;
			int numPlayers = 0;
			boolean attackPlayed = false;
			boolean attacked = false;
			boolean turnOffAttack = false;
			System.out.println("How many players will be playing (2-5)?");
			numPlayers = sc.nextInt();
			Deck deck = new Deck(numPlayers);
			Pile pile = new Pile();

			deck.shuffle();

			ArrayList<Player> players = new ArrayList<>();

			if (numPlayers >= 2) {
				Player player = new Player("Adam", false);
				Player cpu1 = new Player("Stephen", true);
				players.add(player);
				players.add(cpu1);
				for (int i = 0; i < 4; i++) {
					deck.draw(player.getHand());
					deck.draw(cpu1.getHand());

				}
			}

			if (numPlayers >= 3) {
				Player cpu2 = new Player("Ben", true);
				players.add(cpu2);
				for (int i = 0; i < 4; i++) {
					deck.draw(cpu2.getHand());

				}

			}
			if (numPlayers >= 4) {
				Player cpu3 = new Player("Josh", true);
				players.add(cpu3);
				for (int i = 0; i < 4; i++) {
					deck.draw(cpu3.getHand());

				}

			}
			if (numPlayers == 5) {
				Player cpu4 = new Player("Kathy", true);
				players.add(cpu4);
				for (int i = 0; i < 4; i++) {
					deck.draw(cpu4.getHand());

				}
			}

			deck.addDefuses();
			deck.addKittens();
			deck.shuffle();

			Collections.shuffle(players);
			System.out.println("The Players are " + players.toString());
			ArrayList<Player> playersWithDefuse = new ArrayList<>(players.size());
			playersWithDefuse.addAll(players);
			int currentPlayerIndex = 0;
			int lastPlayerIndex = 0;

			while (players.size() > 1) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Player currentPlayer = players.get(currentPlayerIndex);
				System.out.println("-------------------------------------------------------");

				System.out.println(
						"Current player is " + currentPlayer + "." + " Deck has " + deck.size() + " cards left.");
				Hand currentHand = currentPlayer.getHand();
				Card playedCard = null;

				// CPU Player
				if (currentPlayer.isCPU()) {

					// If CPU Hand has Defuse, be less careful
					if (currentHand.contains("Defuse")) {

						// If last card played was Skip, Nope, or Attack, Nope
						if (attackPlayed == true && currentHand.contains("Nope")) {
							playedCard = currentPlayer.getHand().get("Nope");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							int tempPlayerIndex = currentPlayerIndex;
							currentPlayerIndex = lastPlayerIndex;
							lastPlayerIndex = tempPlayerIndex;
							attacked = false;
						}
						//If has a pair, play the pair and take someone's card
						else if (currentPlayer.hasPair()) {
							Card pairCard = currentPlayer.getPair().get(0);
							//If their are players that you know havent played their defuse card, try to take from them
							if (!playersWithDefuse.isEmpty()
									&& !(playersWithDefuse.contains(currentPlayer) && playersWithDefuse.size() == 1)) {
								Player randomPlayerWithDefuse;
								do {
									randomPlayerWithDefuse = playersWithDefuse.get(rand.nextInt(playersWithDefuse.size()));
								} while (randomPlayerWithDefuse == currentPlayer);
								Card randomCard = randomPlayerWithDefuse.getHand()
										.get(rand.nextInt(randomPlayerWithDefuse.getHandSize()));
								randomPlayerWithDefuse.getHand().removeCard(randomCard);
								currentPlayer.getHand().addCard(randomCard);
								currentPlayer.getHand().removeCard(pairCard);
								currentPlayer.getHand().removeCard(pairCard);
								System.out.println(currentPlayer + " takes " + randomCard + " from "
										+ randomPlayerWithDefuse + " by playing a pair of " + pairCard + "s!");
							} 
							//Otherwise, take from someone randomly
							else {
								Player randomPlayer;
								do {
									randomPlayer = players.get(rand.nextInt(players.size()));
								} while (randomPlayer == currentPlayer);
								Card randomCard = randomPlayer.getHand().get(rand.nextInt(randomPlayer.getHandSize()));
								randomPlayer.getHand().removeCard(randomCard);
								currentPlayer.getHand().addCard(randomCard);
								currentPlayer.getHand().removeCard(pairCard);
								currentPlayer.getHand().removeCard(pairCard);
								System.out.println(currentPlayer + " takes " + randomCard + " from " + randomPlayer
										+ " by playing a pair of " + pairCard + "s!");
							}
						}

						// If last card played was skip, nope, or attack, skip
						else if (attackPlayed == true && currentHand.contains("Skip")) {
							playedCard = currentPlayer.getHand().get("Skip");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							if (currentPlayerIndex >= players.size() - 1) {
								lastPlayerIndex = currentPlayerIndex;
								currentPlayerIndex = 0;
								attackPlayed = true;
							} else {
								lastPlayerIndex = currentPlayerIndex;
								currentPlayerIndex++;
								attackPlayed = true;
							}
							attacked = false;

						}
						// If last card played was skip, nope, or attack, attack
						else if (attackPlayed == true && currentHand.contains("Attack")) {
							playedCard = currentPlayer.getHand().get("Attack");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							lastPlayerIndex = currentPlayerIndex;
							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
								attackPlayed = true;
							} else {
								currentPlayerIndex++;
								attackPlayed = true;
							}
							attacked = true;

						}
						// If last card played was skip, nope, or attack, shuffle

						else if (attackPlayed == true && currentHand.contains("Shuffle")) {
							playedCard = currentPlayer.getHand().get("Shuffle");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");
							currentPlayer.getHand().removeCard(playedCard);
							deck.shuffle();
							attackPlayed = false;
						}
					}

					// No defuse in hand, be more careful
					else {

						// If last card played was Skip or Attack or Nope, Nope
						if (attackPlayed == true && currentHand.contains("Nope")) {
							playedCard = currentPlayer.getHand().get("Nope");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");
							currentPlayer.getHand().removeCard(playedCard);
							int tempPlayerIndex = currentPlayerIndex;
							currentPlayerIndex = lastPlayerIndex;
							lastPlayerIndex = tempPlayerIndex;
							attacked = false;

						}
						//If you have a pair, play it
						else if (currentPlayer.hasPair()) {
							Card pairCard = currentPlayer.getPair().get(0);
							//Try to take from someone with a defuse, if they havent played a defuse yet
							if (!playersWithDefuse.isEmpty()
									&& !(playersWithDefuse.contains(currentPlayer) && playersWithDefuse.size() == 1)) {
								Player randomPlayerWithDefuse;
								do {
									randomPlayerWithDefuse = playersWithDefuse.get(rand.nextInt(playersWithDefuse.size()));
								} while (randomPlayerWithDefuse == currentPlayer);
								Card randomCard = randomPlayerWithDefuse.getHand()
										.get(rand.nextInt(randomPlayerWithDefuse.getHandSize()));
								randomPlayerWithDefuse.getHand().removeCard(randomCard);
								currentPlayer.getHand().addCard(randomCard);
								currentPlayer.getHand().removeCard(pairCard);
								currentPlayer.getHand().removeCard(pairCard);
								System.out.println(currentPlayer + " takes " + randomCard + " from "
										+ randomPlayerWithDefuse + " by playing a pair of " + pairCard + "s!");
							} 
							//Otherwise, take from someone randomly
							else {
								Player randomPlayer;
								do {
									randomPlayer = players.get(rand.nextInt(players.size()));
								} while (randomPlayer == currentPlayer);
								Card randomCard = randomPlayer.getHand().get(rand.nextInt(randomPlayer.getHandSize()));
								randomPlayer.getHand().removeCard(randomCard);
								currentPlayer.getHand().addCard(randomCard);
								currentPlayer.getHand().removeCard(pairCard);
								currentPlayer.getHand().removeCard(pairCard);
								System.out.println(currentPlayer + " takes " + randomCard + " from " + randomPlayer
										+ " by playing a pair of " + pairCard + "s!");
							}
						}

						// If last card was Skip, Nope, or Attack, Skip
						else if (attackPlayed == true && currentHand.contains("Skip")) {
							playedCard = currentPlayer.getHand().get("Skip");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							lastPlayerIndex = currentPlayerIndex;
							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
								attackPlayed = true;
							} else {
								currentPlayerIndex++;
								attackPlayed = true;
							}
							attacked = false;

						}

						// If last card was Skip, Attack, or Nope, Attack
						else if (attackPlayed == true && currentHand.contains("Attack")) {
							playedCard = currentPlayer.getHand().get("Attack");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							lastPlayerIndex = currentPlayerIndex;
							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
								attackPlayed = true;
							} else {
								currentPlayerIndex++;
								attackPlayed = true;
							}
							attacked = true;

						}

						// If last card was Skip, Attack, or Nope, Shuffle

						else if (attackPlayed == true && currentHand.contains("Shuffle")) {
							playedCard = currentPlayer.getHand().get("Shuffle");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");
							currentPlayer.getHand().removeCard(playedCard);
							deck.shuffle();
							attackPlayed = false;

						}
						
						//If less than 25 cards left in the deck and you have a See the Future, and a card that can change the future, play it
						else if (deck.size() < 25 && currentHand.contains("See the Future")
								&& (currentHand.contains("Skip") || currentHand.contains("Attack")
										|| currentHand.contains("Shuffle"))) {
							playedCard = currentPlayer.getHand().get("See the Future");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");
							currentPlayer.getHand().removeCard(playedCard);
							attackPlayed = false;

							// If kitten is revealed, play attack if in hand
							if (deck.top().toString() == "Exploding Kitten" && currentHand.contains("Attack")) {
								playedCard = currentPlayer.getHand().get("Attack");
								pile.add(playedCard);
								System.out.println(currentPlayer + " plays " + playedCard + ".");

								currentPlayer.getHand().removeCard(playedCard);
								lastPlayerIndex = currentPlayerIndex;
								if (currentPlayerIndex >= players.size() - 1) {
									currentPlayerIndex = 0;
									attackPlayed = true;
								} else {
									currentPlayerIndex++;
									attackPlayed = true;

								}
								attacked = true;

							}
							//If a kitten is revealed, play skip if you don't have attack
							else if (deck.top().toString() == "Exploding Kitten" && currentHand.contains("Skip")) {
								playedCard = currentPlayer.getHand().get("Skip");
								pile.add(playedCard);
								System.out.println(currentPlayer + " plays " + playedCard + ".");

								currentPlayer.getHand().removeCard(playedCard);
								lastPlayerIndex = currentPlayerIndex;

								if (currentPlayerIndex >= players.size() - 1) {
									currentPlayerIndex = 0;
									attackPlayed = true;
								} else {
									currentPlayerIndex++;
									attackPlayed = true;

								}
								attacked = false;

							}
							//Last resort, play a shuffle if you dont have skip or attack
							else if (deck.top().toString() == "Exploding Kitten" && currentHand.contains("Shuffle")) {
								playedCard = currentPlayer.getHand().get("Shuffle");
								pile.add(playedCard);
								System.out.println(currentPlayer + " plays " + playedCard + ".");
								currentPlayer.getHand().removeCard(playedCard);
								deck.shuffle();
							}

						}
						
						//If you don't have a defuse and less than 18 cards remain, skip your turn
						else if (deck.size() < 15 && currentHand.contains("Skip")) {
							playedCard = currentPlayer.getHand().get("Skip");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							lastPlayerIndex = currentPlayerIndex;

							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
								attackPlayed = true;
							} else {
								currentPlayerIndex++;
								attackPlayed = true;

							}
							attacked = false;

						}
						
						//If deck is down to 15 cards and no defuse, attack
						else if (deck.size() <= 13 && currentHand.contains("Attack")) {
							playedCard = currentPlayer.getHand().get("Attack");
							pile.add(playedCard);
							System.out.println(currentPlayer + " plays " + playedCard + ".");

							currentPlayer.getHand().removeCard(playedCard);
							lastPlayerIndex = currentPlayerIndex;
							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
								attackPlayed = true;
							} else {
								currentPlayerIndex++;
								attackPlayed = true;

							}
							attacked = true;

						}
					}
					
					//If you didn't play an attack card, you gotta draw
					if ((playedCard == null || playedCard.toString() == "See the Future") && attacked == true) {
						System.out.println(currentPlayer + " draws a card after being attacked!");
						deck.draw(currentHand);
						attackPlayed = false;
						if (currentPlayer.getHand().get(currentPlayer.getHandSize() - 1)
								.toString() == "Exploding Kitten") {
							System.out.println(currentPlayer + " drew an Exploding Kitten!");
							if (currentPlayer.hasDefuse()) {
								currentPlayer.getHand().removeCard(new Card("Defuse"));
								System.out.println(currentPlayer + " uses a Defuse Card!");
							} else {
								System.out.println(currentPlayer + " has lost!");
								playerToRemove = currentPlayer;
								players.remove(currentPlayerIndex);
							}
							if(deck.size() == 0) {
								kittyPosition =0;
							}
							else {
							kittyPosition = rand.nextInt(deck.size());
							}
							deck.add(kittyPosition, new Card("Exploding Kitten"));
							currentPlayer.getHand().removeCard(new Card("Exploding Kitten"));

							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
							} else {
								currentPlayerIndex++;
							}
							if (playerToRemove != null) {
								players.remove(playerToRemove);
								playerToRemove = null;
							}
						}
						turnOffAttack = true;

					}

					if ((playedCard == null || playedCard.toString() == "See the Future") && attacked == false) {
						System.out.println(currentPlayer + " draws a card and ends their turn with "
								+ currentHand.size() + " cards.");
						deck.draw(currentHand);
						attackPlayed = false;
						//Check for kitten
						if (currentPlayer.getHand().get(currentPlayer.getHandSize() - 1)
								.toString() == "Exploding Kitten") {
							System.out.println(currentPlayer + " drew an Exploding Kitten!");
							//If they have a defuse card, use it
							if (currentPlayer.hasDefuse()) {
								currentPlayer.getHand().removeCard(new Card("Defuse"));
								System.out.println(currentPlayer + " uses a Defuse Card!");
								playersWithDefuse.remove(currentPlayer);
								//Otherwise, they lose :(
							} else {
								System.out.println(currentPlayer + " has lost!");
								playerToRemove = currentPlayer;
								
							}
							if(deck.size() ==0) {
								kittyPosition = 0;
							}
							else {
							kittyPosition = rand.nextInt(deck.size());}
							deck.add(kittyPosition, new Card("Exploding Kitten"));
							currentPlayer.getHand().removeCard(new Card("Exploding Kitten"));
						}
						if (currentPlayerIndex >= players.size() - 1) {
							currentPlayerIndex = 0;
						} 
						
						else {
						
							currentPlayerIndex++;
						}
						if (playerToRemove != null) {
							players.remove(playerToRemove);
							playerToRemove = null;
							if(currentPlayerIndex > players.size()-1) {
								currentPlayerIndex--;
							}
						}
						attackPlayed = false;

					}
					if (turnOffAttack) {
						attacked = false;
						turnOffAttack = false;
					}

				}

				// Human player
				else {

					if (currentPlayer.hasPair()) {
						System.out.println("You have a pair of " + currentPlayer.getPair());
						System.out.println("Would you like to play a pair? Press 0 to skip.");
						decision = sc.nextInt();
					}

					if (decision == 0) {
					System.out.println("Here is your hand: " + currentPlayer.getHand());

						System.out.println("What card would you like to play? (0 to draw a card and end your turn)");
						int play = sc.nextInt();

						// Draw a card
						if (play == 0) {
							Card cardDrawn = deck.top();
							deck.draw(currentPlayer.getHand());
							attackPlayed = false;
							// Check if it is a kitten
							if (currentPlayer.getHand().get(currentPlayer.getHandSize() - 1)
									.toString() == "Exploding Kitten") {
								System.out.println("You drew an Exploding Kitten!");

								// If player has a defuse card, defuse the
								// kitten
								// and place it back in the deck
								if (currentPlayer.hasDefuse()) {
									currentPlayer.getHand().removeCard(new Card("Defuse"));
									playersWithDefuse.remove(currentPlayer);
									System.out.println("What position would you like to place the kitten (1 - "
											+ (deck.size() + 1) + ")?");
									kittyPosition = sc.nextInt();
									deck.add(kittyPosition - 1, new Card("Exploding Kitten"));
									currentPlayer.getHand().removeCard(new Card("Exploding Kitten"));
									attackPlayed = false;
								} else {
									System.out.println("You lose!");
									players.remove(currentPlayerIndex);
								}
							}
							// Next player's turn
							
							if(attacked && cardDrawn.toString() != "Exploding Kitten") {
								attacked = false;
								System.out.println("You drew " + cardDrawn + " and must draw again!");
							}
							else {
								if(cardDrawn.toString() != "Exploding Kitten") {
								System.out.println("You drew " + cardDrawn + "."); 
								}

							if (currentPlayerIndex >= players.size() - 1) {
								currentPlayerIndex = 0;
							} else {
								currentPlayerIndex++;
							}
						}
						}

						else {
							// Get played card
							playedCard = currentPlayer.getHand().get(play - 1);
							pile.add(playedCard);
							System.out.println("You play " + playedCard + ".");
							currentPlayer.getHand().removeCard(playedCard);

							if (playedCard.toString() == "Skip") {
								attacked = false;
								lastPlayerIndex = currentPlayerIndex;
								if (currentPlayerIndex >= players.size() - 1) {
									currentPlayerIndex = 0;
									attackPlayed = true;
								} else {
									currentPlayerIndex++;
									attackPlayed = true;
								}
							}
							if (playedCard.toString() == "See the Future") {
								System.out.println("The top 3 cards are " + deck.topThree());
							}

							if (playedCard.toString() == "Shuffle") {
								deck.shuffle();
							}
							if (playedCard.toString() == "Attack") {
								lastPlayerIndex = currentPlayerIndex;
								attacked = true;
								if (currentPlayerIndex >= players.size() - 1) {
									currentPlayerIndex = 0;
									attackPlayed = true;
								} else {
									currentPlayerIndex++;
									attackPlayed = true;

								}
							}

							if (playedCard.toString() == "Nope") {
								if (attackPlayed == false) {
									System.out.println("You can't Nope right now! You haven't been attacked!");
								} else if (pile.second().toString() == "Skip" || pile.second().toString() == "Attack"
										|| pile.second().toString() == "Nope") {
									int tempPlayerIndex = currentPlayerIndex;
									currentPlayerIndex = lastPlayerIndex;
									lastPlayerIndex = tempPlayerIndex;
									attackPlayed = true;
								}
							}
							
							if( playedCard.toString() == "Favor") {
								ArrayList<Player> tempPlayers = new ArrayList<>();
								for(int i = 0; i < players.size(); i++) {
									if(players.get(i) != currentPlayer) {
									tempPlayers.add(players.get(i));
									}
								}
								System.out.println("Who do you want to take a card from? " + tempPlayers);
								playerToTakeFromIndex = sc.nextInt();
								Player playerToTakeFrom = tempPlayers.get(playerToTakeFromIndex - 1);
								Card worstCard = new Card(playerToTakeFrom.getWorst());
								playerToTakeFrom.getHand().removeCard(worstCard);
								currentPlayer.getHand().addCard(worstCard);
								System.out.println("You take a " + worstCard + " from " + playerToTakeFrom + "!");
							}
						}

					} else {
						ArrayList<Card> pairs;
						pairs = currentPlayer.getPair();
						System.out.println("You play a pair of " + pairs.get(decision - 1));
						currentPlayer.getHand().removeCard(pairs.get(decision - 1));
						currentPlayer.getHand().removeCard(pairs.get(decision - 1));
						ArrayList<Player> tempPlayers = new ArrayList<>();
						for(int i = 0; i < players.size(); i++) {
							if(players.get(i) != currentPlayer) {
							tempPlayers.add(players.get(i));
							}
						}
						System.out.println("Who do you want to take a card from? " + tempPlayers);
						playerToTakeFromIndex = sc.nextInt();
						Player playerToTakeFrom = tempPlayers.get(playerToTakeFromIndex - 1);
						int randomCardIndex = rand.nextInt(playerToTakeFrom.getHandSize());
						Card takenCard = playerToTakeFrom.getHand().get(randomCardIndex);
						currentPlayer.getHand().addCard(takenCard);
						playerToTakeFrom.getHand().removeCard(takenCard);
						System.out.println("You take " + takenCard + " from " + playerToTakeFrom + ".");
						decision = 0;
					}
				}

			}

			System.out.println(players.get(0) + " has won!");
		}
	}
}
