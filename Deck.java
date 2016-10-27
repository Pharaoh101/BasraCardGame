package CardGames.Basra;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class Deck {
	
	private String[] deck = {"Ace Spades", "King Spades", "Queen Spades", "Jack Spades", "10 Spades", "9 Spades", "8 Spades", "7 Spades", "6 Spades", "5 Spades", "4 Spades", "3 Spades", "2 Spades", 
			"Ace Hearts", "King Hearts", "Queen Hearts", "Jack Hearts", "10 Hearts", "9 Hearts", "8 Hearts", "7 Hearts", "6 Hearts", "5 Hearts", "4 Hearts", "3 Hearts", "2 Hearts",
			"Ace Diamonds", "King Diamonds", "Queen Diamonds", "Jack Diamonds", "10 Diamonds", "9 Diamonds", "8 Diamonds", "7 Diamonds", "6 Diamonds", "5 Diamonds", "4 Diamonds", "3 Diamonds", "2 Diamonds",
			"Ace Clubs", "King Clubs", "Queen Clubs", "Jack Clubs", "10 Clubs", "9 Clubs", "8 Clubs", "7 Clubs", "6 Clubs", "5 Clubs", "4 Clubs", "3 Clubs", "2 Clubs"};
	private String[] tempShuffledDeck;
	protected Vector<String> shuffledDeck;

	
	public Deck() {
		shuffleDeck();
	}
	
	//Generate new random int each loop and swap position i with that int to replicate shuffling
	private void shuffleDeck() {
		tempShuffledDeck = Arrays.copyOf(deck, deck.length);
		Random randomPos = new Random();
		for (int i = 0; i < tempShuffledDeck.length; i++) {
			int nextInt = randomPos.nextInt(tempShuffledDeck.length);
			String currentCard = tempShuffledDeck[i];
			String swapCard = tempShuffledDeck[nextInt];
			tempShuffledDeck[i] = swapCard;
			tempShuffledDeck[nextInt] = currentCard;
		}
		
		//Convert to Vector for easier removal of cards
		shuffledDeck = new Vector<String>();
		for (int i = 0; i < tempShuffledDeck.length; i++) {
			shuffledDeck.add(tempShuffledDeck[i]);
		}
	}
	
	protected Vector<String> dealHand(int numCards) {
		Vector<String> hand = new Vector<String>();
		String card;
		int number = 0;
		while (number != numCards) {
			card = shuffledDeck.remove(0);
			hand.add(card);
			number += 1;
		}
		
		return hand;
	}
	
	protected Vector<String> dealFloor(int numCards) {
		Vector<String> floor = new Vector<String>();
		String card;
		int number = 0;
		while (number != numCards) {
			card = shuffledDeck.remove(0);
			
			if (card.contains("Jack") || card.equals("7 Diamonds")) {
				shuffledDeck.add(card);
				continue;
			}
			
			floor.add(card);
			number += 1;
		}
		
		return floor;
	}
	
	private void printArray(String[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
	}
	
	protected void printVector(Vector<String> vector) {
		for (int i = 0; i < vector.size(); i++) {
			System.out.print(vector.get(i) + ", ");
		}
		
		System.out.println();
	}
	
	public static void main(String[] args) {
		Deck test = new Deck();
		//test.printArray(test.deck);
		test.printArray(test.tempShuffledDeck);
		test.printVector(test.shuffledDeck);
		System.out.println();
		test.printVector(test.dealHand(13));
		System.out.println();
		test.printVector(test.shuffledDeck);
		
	}
}