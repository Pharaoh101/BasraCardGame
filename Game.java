package CardGames.Basra;

import java.util.Scanner;
import java.util.Vector;

public class Game {

	private Vector<String> floor;
	private int floorValue; // sum of card values on floor, -1 if floor contains a picture card or total value is above 10
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	private Boolean firstPlay = true;

	public Game() {
		initGame();
	}

	private void initGame() {
		p1 = new Player();
		p2 = new Player();
		p3 = new Player();
		p4 = new Player();
	}

	private void dealCards(Deck deck, Boolean dealToFloor) {
		p1.hand = deck.dealHand(4);
		p2.hand = deck.dealHand(4);
		p3.hand = deck.dealHand(4);
		p4.hand = deck.dealHand(4);

		if (dealToFloor) {
			floor = deck.dealFloor(4);
			updateFloorValue();
		}
	}

	private void play(int playerNum, Scanner chooseCard) {
		boolean validChoice = false;
		String cardChosen = "";
		System.out.println("PLAYER: " + playerNum);
		System.out.println("This is the state of the floor:");
		printVector(floor);
		System.out.println(floorValue);
		System.out.println();
		System.out.println("Please select which card you wish to play");
		System.out.println();
		System.out.println("This is your hand:");
		if (playerNum == 1) {
			while (!validChoice) {
				p1.printVector(p1.hand);
				//Scanner chooseCard = new Scanner(System.in);
				cardChosen = chooseCard.nextLine();

				if (!p1.hand.contains(cardChosen)) {
					System.out.println("Please choose a card to play that is in your hand");
				}

				else {
					//chooseCard.close();
					validChoice = true;
				}
			}

			p1.playCard(cardChosen);
		}

		if (playerNum == 2) {
			while (!validChoice) {
				p2.printVector(p2.hand);
				//Scanner chooseCard = new Scanner(System.in);
				cardChosen = chooseCard.nextLine();

				if (!p2.hand.contains(cardChosen)) {
					System.out.println("Please choose a card to play that is in your hand");
				}

				else {
					//chooseCard.close();
					validChoice = true;
				}
			}

			p2.playCard(cardChosen);
		}

		if (playerNum == 3) {
			while (!validChoice) {
				p3.printVector(p3.hand);
				//Scanner chooseCard = new Scanner(System.in);
				cardChosen = chooseCard.nextLine();

				if (!p3.hand.contains(cardChosen)) {
					System.out.println("Please choose a card to play that is in your hand");
				}

				else {
					//chooseCard.close();
					validChoice = true;
				}
			}

			p3.playCard(cardChosen);
		}

		if (playerNum == 4) {
			while (!validChoice) {
				p4.printVector(p4.hand);
				//Scanner chooseCard = new Scanner(System.in);
				cardChosen = chooseCard.nextLine();

				if (!p4.hand.contains(cardChosen)) {
					System.out.println("Please choose a card to play that is in your hand");
				}

				else {
					//chooseCard.close();
					validChoice = true;
				}
			}

			p4.playCard(cardChosen);
		}

		updateGame(playerNum, cardChosen);
	}

	private void updateGame(int playerNum, String cardChosen) {
		updateFloor(playerNum, cardChosen);

		// Check to see if deck is empty, signaling the end of the round
	}

	private void updateFloor(int playerNum, String cardChosen) {
		// Tracks which cards have been taken by the players actions, if any
		Vector<String> cardsTakenFromFloor = new Vector<String>();

		// Boolean array, showing which values have basras available
		Vector<Boolean> basras = new Vector<Boolean>();
		basras = checkForBasra();

		// Finds value of card e.g. Ace, King, 7, 8 etc.
		int whiteSpacePos = cardChosen.indexOf(' ');
		String cardChosenValue = cardChosen.substring(0, whiteSpacePos);
		if (cardChosenValue.equalsIgnoreCase("Jack")) {

			// If there are no cards on the floor, the Jack has no effect and is played like a normal card
			if (floor.size() == 0) {
				floor.add(cardChosen);
			}

			else if (floor.size() == 1) {
				int whiteSpace = floor.get(0).indexOf(' ');
				String floorCardValue = floor.get(0).substring(0, whiteSpace);
				cardsTakenFromFloor.add(floor.get(0));

				// If floor card is a Jack then player gets a 20 point Basra bonus
				// Same occurs if floor card is 7 Diamonds
				if ((floorCardValue.equals(cardChosenValue)) || (floor.get(0).equals("7 Diamonds"))) {
					collectCards(playerNum, cardsTakenFromFloor, 20);
				}

				// If card on floor is not special, no bonus points are given
				else {
					collectCards(playerNum, cardsTakenFromFloor, 0);
				}
			}

			// If Jack is played, player collects all cards
			else if (floor.size() > 1) {
				for (int i = 0; i < floor.size(); i++) {
					cardsTakenFromFloor.add(floor.get(i));
				}

				collectCards(playerNum, cardsTakenFromFloor, 0);
			}
		}

		else if (cardChosen.equalsIgnoreCase("7 Diamonds")) {
			// If there are no cards on the floor, the 7 Diamonds has no effect and is played like a normal card
			if (floor.size() == 0) {
				floor.add(cardChosen);
			}

			else if (floor.size() == 1) {
				int whiteSpace = floor.get(0).indexOf(' ');
				String floorCardValue = floor.get(0).substring(0, whiteSpace);
				cardsTakenFromFloor.add(floor.get(0));

				// If floor card is a Jack then player gets a 20 point Basra bonus
				// Same occurs if floor card has a value of 7 from any suit
				if ((floorCardValue.equals(cardChosenValue)) || (floorCardValue.equals("Jack"))) {
					collectCards(playerNum, cardsTakenFromFloor, 20);
				}

				// Any other floor card is taken with a Basra of 10 points
				else {
					collectCards(playerNum, cardsTakenFromFloor, 10);
				}
			}

			else if (floor.size() > 1) {
				for (int i = 0; i < floor.size(); i++) {
					cardsTakenFromFloor.add(floor.get(i));
				}

				// All cards are taken regardless, this just checks to see if there is a basra available
				if (basras.contains(true)) {
					collectCards(playerNum, cardsTakenFromFloor, 10);
				}

				else {
					collectCards(playerNum, cardsTakenFromFloor, 0);
				}
			}
		}

		// For any other card that is played
		else {
			// If floor is empty, play the card with no effect
			if (floor.size() == 0) {
				floor.add(cardChosen);
			}

			else if (floor.size() == 1) {
				int whiteSpace = floor.get(0).indexOf(' ');
				String floorCardValue = floor.get(0).substring(0, whiteSpace);

				// If the floor card value matches the card chosen's value, 10 points are awarded
				// However if floor card is 7 Diamonds and the card played is a 7, 20 points are awarded
				if (floorCardValue.equals(cardChosenValue)) {
					cardsTakenFromFloor.add(floor.get(0));

					if (cardChosenValue.equals("7") && floor.get(0).equals("7 Diamonds")) {
						collectCards(playerNum, cardsTakenFromFloor, 20);
					}

					else {
						collectCards(playerNum, cardsTakenFromFloor, 10);
					}
				}

				// Otherwise, just play the card to the floor
				// Unless the floor card is 7 Diamonds, in which case any card played counts as a 10 point Basra
				else {
					if (floor.get(0).equals("7 Diamonds")) {
						cardsTakenFromFloor.add(floor.get(0));
						collectCards(playerNum, cardsTakenFromFloor, 10);
					}

					else {
						floor.add(cardChosen);
					}
				}
			}

			// Three scenarios exist with a floor of size greater than 1:
			// 1. There is a basra available (total value is less than 10 - note we dealt with basra single picture cards above
			// 2. 1 or more cards may be taken by the card played
			// 3. There is no match and the card is simply played on the floor
			else if (floor.size() > 1) {

				// Cards available for each possible card to be played from hand, bar 7 Diamonds and Jack
				Object[] cardsAvailable = checkFloor();

				int value = 0;
				if (cardChosenValue.equals("Queen")) {
					value = 11;
				}

				else if (cardChosenValue.equals("King")) {
					value = 12;
				}

				else if (cardChosenValue.equals("Ace")) {
					value = 1;
				}

				else {
					value = Integer.parseInt(cardChosenValue);
				}

				Object validCards = cardsAvailable[value - 1];

				// Basra available, collect all cards and reward 10 bonus points
				if (validCards == (Boolean) true) {
					for (int i = 0; i < floor.size(); i++) {
						cardsTakenFromFloor.add(floor.get(i));
					}

					collectCards(playerNum, cardsTakenFromFloor, 10);
				}

				// No cards taken, just add the card played to the floor
				else if (validCards == (Boolean) false) {
					floor.add(cardChosen);
				}

				// Some cards are taken, some are left, no bonus points
				else {
					for (int i = 0; i < ((Vector<String>) validCards).size(); i++) {
						int pos = floor.indexOf(((Vector<String>) validCards).get(i));
						cardsTakenFromFloor.add(floor.get(pos));
					}

					collectCards(playerNum, cardsTakenFromFloor, 0);
				}
			}

			printVector(cardsTakenFromFloor);
		}
	}

	// Loops through all possible values and finds if there are any basras or combinations possible
	// Stored in the Object[] array
	// Basra simply puts a true value
	// If cards can be taken they are placed in their own array and placed in the relevant part of available
	// If nothing is possible a false value is entered
	private Object[] checkFloor() {
		Object[] available = new Object[12];
		String value;
		for (int i = 1; i < 13; i++) {
			if (i == 1) {
				value = "Ace";
			}

			else if (i == 11) {
				value = "Queen";
			}

			else if (i == 12) {
				value = "King";
			}

			else {
				Integer intI = i;
				value = intI.toString();
			}

			Vector<String> cardsTaken = new Vector<String>();

			// Check if there are any cards that can be taken - not including combos
			for (int j = 0; j < floor.size(); j++) {
				int whiteSpacePos = floor.get(j).indexOf(' ');
				String floorCardValue = floor.get(j).substring(0, whiteSpacePos);

				if (floorCardValue.equals(value)) {
					cardsTaken.add(floor.get(j));
				}
			}

			// The cards on the floor were duplicates and this counts as a basra
			// The cards have clearly all been search through at this point, so skip to next iteration
			if (cardsTaken.size() == floor.size()) {
				available[i - 1] = true;
				continue;
			}

			// Now look for combos
			// Note that Ace, Queen and King cannot be the result of a combo
			if (!value.equals("Ace") && !value.equals("Queen") && !value.equals("King")) {
				for (int j = 0; j < floor.size(); j++) {

					// Temporary storage for possible combos
					Vector<String> tempCombos = new Vector<String>();

					String firstCard = floor.get(j);
					int whiteSpaceFirst = firstCard.indexOf(' ');
					String firstFloorValue = firstCard.substring(0, whiteSpaceFirst);

					// Jacks, Queens and Kings and the original value can't be part of any combination
					if (firstFloorValue.equals("Jack") || firstFloorValue.equals("Queen") || firstFloorValue.equals("King")
							|| firstFloorValue.equals(value)) {
						continue;
					}

					// Add this card to the tempCombos
					tempCombos.add(firstCard);

					// j + 1 because index starts at 0 and ends at 1 below actual size of vector
					int cardsSearched = j + 1;
					int sum = 0;

					if (firstFloorValue.length() == 3) {
						sum = 1;
					}

					else {
						sum = Integer.parseInt(firstFloorValue);
					}

					// Number of cards after the first card in the temporary combo
					int numCardsAfter = 0;

					// While not all cards have been searched
					while (cardsSearched < floor.size()) {
						numCardsAfter += 1;
						cardsSearched += numCardsAfter;

						String nextCard = floor.get(j + numCardsAfter);
						int whiteSpaceNext = nextCard.indexOf(' ');
						String nextFloorValue = nextCard.substring(0, whiteSpaceNext);
						int nextValue = 0;

						// Jacks, Queens and Kings can't be part of any combination
						if (nextFloorValue.equals("Jack") || nextFloorValue.equals("Queen") || nextFloorValue.equals("King")
								|| nextFloorValue.equals(value)) {
							continue;
						}

						else if (nextFloorValue.equals("Ace")) {
							nextValue = 1;
						}

						else {
							nextValue = Integer.parseInt(nextFloorValue);
						}

						// Carry the total sum found so far
						sum += nextValue;

						// If the sum exceeds the value of the card played then the floor card in question will be skipped
						//over and the next floor card will be taken into account
						if (sum > i) {
							sum -= nextValue;
							continue;
						}

						// If it's less than that sum then the floor card just added should be temporarily noted in 
						//case it can be used as part of a bigger sum
						if (sum < i) {
							tempCombos.add(nextCard);
						}

						// But if the exact value is found, then the loop will break and all cards used in this sum are saved
						if (sum == i) {
							tempCombos.add(nextCard);
							for (int k = 0; k < tempCombos.size(); k++) {
								cardsTaken.add(tempCombos.get(k));
							}

							break;
						}
					}
				}
			}

			// If all the cards on the floor where taken by a cards that isn't a Jack or 7D then it was a Basra
			if (cardsTaken.size() == floor.size()) {
				available[i - 1] = true;
			}

			// If no cards where taken then enter false, to indicate no cards eaten
			else if (cardsTaken.size() == 0) {
				available[i - 1] = false;
			}

			// Otherwise, just enter the specific card(s) that can be taken
			else {
				available[i - 1] = cardsTaken;
			}
		}

		for (int j = 0; j < available.length; j++) {
			System.out.println(available[j]);
		}

		return available;
	}

	private Object[] checkFloor(String value) {
		Object[] result = new Object[2];

		// Track cards taken
		Vector<String> cardsTaken = new Vector<String>();

		// Check if there are any cards that can be taken - not including combinations
		for (int i = 0; i < floor.size(); i++) {
			int whiteSpacePos = floor.get(i).indexOf(' ');
			String floorCardValue = floor.get(i).substring(0, whiteSpacePos);

			if (floorCardValue.equals(value)) {
				cardsTaken.add(floor.get(i));
			}
		}
		
		// Track cards taken
		Vector<Vector<String>> combosTaken = new Vector<Vector<String>>();
		
		// Aces, Queens and Kings cannot be the result of any combinations
		if (!value.equals("Ace") && !value.equals("Queen") && !value.equals("King")) {
			for (int i = 0; i < floor.size(); i++) {
				
				String initialCard = floor.get(i);
				
				// Skip this card if it has already been used in a combo
				if (cardsTaken.contains(initialCard)) {
					continue;
				}
				
				int whiteSpacePos = initialCard.indexOf(' ');
				String initialCardValue = initialCard.substring(0, whiteSpacePos);

				int intCardValue = 0;
				int intValue = Integer.parseInt(value);

				// The original value, Jacks, Queens and Kings cannot contribute to combinations
				if (initialCardValue.equals(value) || initialCardValue.equals("Queen") || initialCardValue.equals("King")
						|| initialCardValue.equals("Jack")) {
					continue;
				}

				else if (initialCardValue.equals("Ace")) {
					intCardValue = 1;
				}

				else {
					intCardValue = Integer.parseInt(initialCardValue);

					// If the value of the first card is greater than the value of the card played, 
					// then no combination can be made, so skip this floor card 
					if (intCardValue > intValue) {
						continue;
					}
				}

				int sum = intCardValue;
				int numCardsAfter = 0;
				Vector<String> tempCardsTaken = new Vector<String>();
				tempCardsTaken.add(initialCard);
				
				while (true) {
					numCardsAfter++;
					
					String nextCard = "";
					
					try {
						nextCard = floor.get(i + numCardsAfter);
					}
					
					// If there is an array out of bounds exception then we have reached the end of the floor for this
					// iterations, must break and a new initial card
					catch (Exception e) {
						System.out.println(e);
						break;
					}
					
					int nextWhiteSpacePos = nextCard.indexOf(' ');
					String nextCardValue = initialCard.substring(0, nextWhiteSpacePos);
					int intNextCardValue = 0;

					// The original value, Jacks, Queens and Kings cannot contribute to combinations
					// Also skip past any card that has already been used in a combo
					if (nextCardValue.equals(value) || nextCardValue.equals("Queen") || nextCardValue.equals("King")
							|| nextCardValue.equals("Jack") || cardsTaken.contains(nextCard)) {
						continue;
					}

					else if (nextCardValue.equals("Ace")) {
						intNextCardValue = 1;
					}

					else {
						intNextCardValue = Integer.parseInt(nextCardValue);
					}
					
					sum += intNextCardValue;
					
					if (sum < intValue) {
						tempCardsTaken.add(nextCard);
						continue;
					}
					
					else if (sum == intValue) {
						tempCardsTaken.add(nextCard);
						
						/*for (int j = 0; j < tempCardsTaken.size(); j++) {
							cardsTaken.add(tempCardsTaken.get(j));
						}*/
						
						combosTaken.add(tempCardsTaken);
						
						tempCardsTaken.removeAllElements();
						break;
					}
					
					else if (sum > intValue) {
						sum -= intNextCardValue;
						continue;
					}
				}
			}
		}

		return result;
	}

	// Check floor for any possible basras
	// Basras array represents these values: Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Queen, King
	// Jack is omitted because it is dealt with in updateFloor method
	private Vector<Boolean> checkForBasra() {
		Vector<Boolean> basras = new Vector<Boolean>();
		for (int i = 0; i < 12; i++) {
			basras.add(false);
		}

		if (floor.size() == 0) {
			return basras;
		}

		else if (floor.size() == 1) {
			for (int i = 0; i < basras.size(); i++) {
				basras.set(i, true);
			}

			return basras;
		}

		else if (floor.size() > 1) {
			if (floorValue == -1) {
				return basras;
			}

			else {
				basras.set(floorValue - 1, true);
			}
		}

		return basras;
	}

	private void collectCards(int playerNum, Vector<String> cardsTaken, int scoreIncrease) {
		// Cards taken is always incremented by one to accommodate for the card that was played
		switch (playerNum) {
		case 1:
			p1.numCardsTaken += (cardsTaken.size() + 1);
			p1.score += scoreIncrease;
			break;

		case 2:
			p2.numCardsTaken += (cardsTaken.size() + 1);
			p2.score += scoreIncrease;
			break;

		case 3:
			p3.numCardsTaken += (cardsTaken.size() + 1);
			p3.score += scoreIncrease;
			break;

		case 4:
			p4.numCardsTaken += (cardsTaken.size() + 1);
			p4.score += scoreIncrease;
			break;
		}

		for (int i = 0; i < cardsTaken.size(); i++) {
			String cardTaken = cardsTaken.get(i);
			int floorPos = floor.indexOf(cardTaken);
			floor.removeElementAt(floorPos);
		}
	}

	private void updateFloorValue() {
		floorValue = 0;
		for (int i = 0; i < floor.size(); i++) {
			String floorCard = floor.elementAt(i);
			int whiteSpacePos = floorCard.indexOf(' ');
			String floorCardValue = floorCard.substring(0, whiteSpacePos);
			// Indicates an integer value
			if (floorCardValue.length() == 1 || floorCardValue.length() == 2) {
				int intValue = Integer.parseInt(floorCardValue);
				floorValue += intValue;

				if (floorValue > 10) {
					floorValue = -1;
					break;
				}
			}

			// Indicates an Ace, which in Basra represents the value of 1
			else if (floorCardValue.length() == 3) {
				floorValue += 1;

				if (floorValue > 10) {
					floorValue = -1;
					break;
				}
			}

			// Indicates a picture card
			else if (floorCardValue.length() > 3) {
				floorValue = -1;
				break;
			}
		}
	}

	// Allows user to input the number of players in the game, either 2 or 4
	private int numberOfPlayers() {
		System.out.println("Please input the number of players, either 2 or 4");
		int numPlayers = 0;
		Scanner input = new Scanner(System.in);

		while (true) {
			String number = input.nextLine();
			numPlayers = Integer.parseInt(number);

			if (numPlayers != 2 && numPlayers != 4) {
				System.out.println("Please enter either 2 or 4");
			}

			else {
				break;
			}
		}

		input.close();
		return numPlayers;
	}

	protected void printVector(Vector<String> vector) {
		for (int i = 0; i < vector.size(); i++) {
			System.out.print(vector.get(i) + ", ");
		}

		System.out.println();
	}

	public static void main(String[] args) {
		Game basra = new Game();
		/*int numPlayers = basra.numberOfPlayers();
		String[] players;

		if (numPlayers == 2) {
			players = new String[2];
			players[0] = "1";
			players[1] = "2";
		}

		else if (numPlayers == 4) {
			players = new String[4];
			players[0] = "1";
			players[1] = "2";
			players[2] = "3";
			players[3] = "4";
		}*/

		Scanner chooseCard = new Scanner(System.in);

		// Loop through each player, each deals until deck is depleted
		for (int i = 0; i < 4; i++) {
			Deck deck = new Deck();
			basra.dealCards(deck, true);

			int player = i + 1;
			int numPlayer = player;
			// Round continues until every player's hand is empty
			while (true) {
				basra.play(numPlayer, chooseCard);

				// When all hands are empty, re-deal cards (except for floor)
				if (basra.p1.hand.isEmpty() && basra.p2.hand.isEmpty() && basra.p3.hand.isEmpty() 
						&& basra.p4.hand.isEmpty()) {
					basra.dealCards(deck, false);
					System.out.println("Size of Deck remaining: " + deck.shuffledDeck.size());
				}

				// When the deck is empty, break to tally cards taken, assign scores
				if (deck.shuffledDeck.isEmpty()) {
					break;
				}

				numPlayer++;
				if (numPlayer > 4) {
					numPlayer = 1;
				}
			}

			int[] cardsTakenEachPlayer = new int[4];
			cardsTakenEachPlayer[0] = basra.p1.numCardsTaken;
			cardsTakenEachPlayer[1] = basra.p2.numCardsTaken;
			cardsTakenEachPlayer[2] = basra.p3.numCardsTaken;
			cardsTakenEachPlayer[3] = basra.p4.numCardsTaken;

			Vector<Integer> bestPlayers = new Vector<Integer>();
			int bestLoot = 0;
			for (int j = 0; j < cardsTakenEachPlayer.length; j++) {
				if (cardsTakenEachPlayer[j] > bestLoot) {
					bestPlayers.removeAllElements();
					bestPlayers.add(j + 1);
					bestLoot = cardsTakenEachPlayer[j];
				}

				else if (cardsTakenEachPlayer[j] == bestLoot) {
					bestPlayers.add(j + 1);
				}
			}

			if (bestPlayers.size() == 1) {
				switch (bestPlayers.get(0)) {
				case 1:
					basra.p1.score += 30;
					break;

				case 2:
					basra.p2.score += 30;
					break;

				case 3:
					basra.p3.score += 30;
					break;

				case 4:
					basra.p4.score += 30;
					break;
				}
			}

			else if (bestPlayers.size() == 2) {
				for (int j = 0; j < bestPlayers.size(); j++) {
					switch (bestPlayers.get(j)) {
					case 1:
						basra.p1.score += 20;
						break;

					case 2:
						basra.p2.score += 20;
						break;

					case 3:
						basra.p3.score += 20;
						break;

					case 4:
						basra.p4.score += 20;
						break;
					}
				}
			}

			else if (bestPlayers.size() == 3) {
				for (int j = 0; j < bestPlayers.size(); j++) {
					switch (bestPlayers.get(j)) {
					case 1:
						basra.p1.score += 10;
						break;

					case 2:
						basra.p2.score += 10;
						break;

					case 3:
						basra.p3.score += 10;
						break;

					case 4:
						basra.p4.score += 10;
						break;
					}
				}
			}
		}

		chooseCard.close();
	}
}