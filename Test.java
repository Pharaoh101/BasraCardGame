package CardGames.Basra;

import java.util.Vector;

public class Test {
	
	private Vector<String> floor;
	
	// Parameter is the value of the card played
	// 
	private void checkFloor(String value) {
		
		Vector<String> cardsTaken = new Vector<String>();
		Vector<String> possibleCards = new Vector<String>();
		
		int floorCardIntValue = 0;
		
		// Find all cards which are smaller than the value of the card played
		// Therefore these all could be used as part of a sum to create the value
		int intValue = Integer.parseInt(value);
		for (int i = 0; i < floor.size(); i++) {
			String floorCard = floor.get(i);
			int floorCardSpace = floorCard.indexOf(' ');
			String floorCardValue = floorCard.substring(0, floorCardSpace);
			
			// Picture cards cannot be used as part of any combinations
			if (floorCardValue.equals("Jack") || floorCardValue.equals("Queen") || floorCardValue.equals("King")) {
				continue;
			}
			
			else if (floorCardValue.equals("Ace")) {
				floorCardIntValue = 1;
			}
			
			else {
				floorCardIntValue = Integer.parseInt(floorCardValue);
			}
			
			// If the value of the floor card is strictly less than the value of the card played then it could be used
			// in a combination
			if (floorCardIntValue < intValue) {
				possibleCards.add(floorCard);
			}
			
			// If the floor card has the same value as the card being played then it will definitely be taken
			else if (floorCardIntValue == intValue) {
				cardsTaken.add(floorCard);
			}
		}
		
		// The tracker is initialized which tracks which floor cards have been used in the summation
		// True = used in the summation
		// False = not used in summation
		// Null = not a suitable option
		Boolean[] tracker = new Boolean[possibleCards.size()];
		for (int i = 0; i < possibleCards.size(); i++) {
			tracker[i] = false;
		}
		
		// Combinations found are stored here
		Vector<Vector<String>> combinations = new Vector<Vector<String>>();
		
		// Any cards with values strictly less than the value of the card played are search through here for combinations
		for (int i = 0; i < possibleCards.size(); i++) {
			Vector<Boolean[]> successfulStates = new Vector<Boolean[]>();
			Vector<Integer> indexChanged = new Vector<Integer>();
			
			int firstCardIntValue = getCardValue(possibleCards.get(i));
			tracker[i] = true;
			
			// Given the starting sum, find all cards on the floor which can now not be used in a combination
			// Set them as null so that they are not needlessly used
			int currentSum = firstCardIntValue;
			tracker = eliminatePossibilities(possibleCards, i, currentSum, intValue, tracker);
			
			// Keep track of trackers which are successful i.e. still potentially viable combinations
			successfulStates.add(tracker);
			
			// Also track the last index of the array which was altered, so it is known which one needs changing
			indexChanged.add(i);
			
			// Loop through rest of the cards, searching for any possible combinations
			int count = i;
			while (true) {
				
				//printBooleanArray(successfulStates.get(successfulStates.size() - 1));
				printSuccessfulStates(successfulStates);
				System.out.println();
				
				count++;
				
				// Reached end of search
				if (count >= possibleCards.size()) {
					break;
				}
				
				System.out.println("Here");
				
				// If the card has already been ruled out, skip to next iteration
				if (tracker[count] == null) {
					continue;
				}
				
				// If the card is not currently in the combination, try to include it
				else if (tracker[count] == false) {
					tracker[count] = true;
					
					// Card added satisfies the requirement that the sum be below the value of the card played
					// Therefore save these states to go back to them if necessary
					if (check(possibleCards, intValue, tracker)) {
						successfulStates.add(tracker);
						indexChanged.add(count);
						continue;
					}
					
					// If the card failed, note down the the card will not be part of the combination and 
					// continue searching
					else if (check(possibleCards, intValue, tracker) == false) {
						tracker[count] = null;
						successfulStates.add(tracker);
						indexChanged.add(count);
					}
				}
			}
		}
	}
	
	private Boolean[] eliminatePossibilities(Vector<String> possibleCards, int currentPos, int currentSum, int intValue, Boolean[] tracker) {
		
		for (int i = currentPos + 1; i < possibleCards.size(); i++) {
			int nextCardIntValue = getCardValue(possibleCards.get(i));
			
			if (nextCardIntValue + currentSum > intValue) {
				tracker[i] = null;
			}
		}
		
		return tracker;
	}
	
	private Boolean check(Vector<String> possibleCards, int intValue, Boolean[] tracker) {
		int sum = 0;
		for (int i = 0; i < tracker.length; i++) {
			if (tracker[i] == null) {
				continue;
			}
			
			else if (tracker[i] == true) {
				int value = getCardValue(possibleCards.get(i));
				sum += value;
				
				if (sum > intValue) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private int totalSum(Vector<String> possibleCards, Boolean[] tracker) {
		int sum = 0;
		
		for (int i = 0; i < tracker.length; i++) {
			if (tracker[i] == true) {
				int cardIntValue = getCardValue(possibleCards.get(i));
				sum += cardIntValue;
			}
		}
		
		return sum;
	}
	
	private int getCardValue(String card) {
		int cardSpace = card.indexOf(' ');
		String cardValue = card.substring(0, cardSpace);
		int cardIntValue = Integer.parseInt(cardValue);
		
		return cardIntValue;
	}
	
	private void printStringVector(Vector<String> vector) {
		for (int i = 0; i < vector.size(); i++) {
			System.out.println(vector.get(i));
		}
	}
	
	private void printBooleanVector(Vector<Boolean> vector) {
		for (int i = 0; i < vector.size(); i++) {
			System.out.println(vector.get(i));
		}
	}
	
	private void printIntegerVector(Vector<Integer> vector) {
		for (int i = 0; i < vector.size(); i++) {
			System.out.println(vector.get(i));
		}
	}
	
	private void printBooleanArray(Boolean[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		
		System.out.println("-----");
	}
	
	private void printSuccessfulStates(Vector<Boolean[]> successfulStates) {
		for (int i = 0; i < successfulStates.size(); i++) {
			printBooleanArray(successfulStates.get(i));
		}
	}
	
	public static void main(String args[]) {
		//2 3 8 4 10 9 6
		Test test = new Test();
		test.floor = new Vector<String>();
		test.floor.addElement("2 Hearts");
		test.floor.addElement("Queen Spades");
		test.floor.addElement("3 Spades");
		test.floor.addElement("8 Clubs");
		test.floor.addElement("4 Clubs");
		test.floor.addElement("9 Clubs");
		test.floor.addElement("6 Clubs");
		test.floor.addElement("10 Clubs");
		
		test.checkFloor("7");
	}
}