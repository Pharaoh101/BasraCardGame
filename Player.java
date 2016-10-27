package CardGames.Basra;

import java.util.Vector;

public class Player {
	
	protected Vector<String> hand;
	protected int score;
	protected int numCardsTaken;
	
	public Player() {
		score = 0;
		numCardsTaken = 0;
	}
	
	protected void playCard(String card) {
		int position = hand.indexOf(card);
		hand.remove(position);
	}
	
	protected void printVector(Vector<String> vector) {
		for (int i = 0; i < vector.size(); i++) {
			System.out.print(vector.get(i) + ", ");
		}
		
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		/*Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		
		Deck deck = new Deck();
		deck.printVector(deck.shuffledDeck);
		
		p1.hand = deck.dealHand(4);
		p2.hand = deck.dealHand(4);
		p3.hand = deck.dealHand(4);
		p4.hand = deck.dealHand(4);
		
		deck.printVector(deck.shuffledDeck);
		p1.printVector(p1.hand);
		p2.printVector(p2.hand);
		p3.printVector(p3.hand);
		p4.printVector(p4.hand);*/
		
		/*String card = p1.playCard(p1.hand.get(0));
		System.out.println(card);
		p1.printVector(p1.hand);*/
		
		Vector<Integer> test = new Vector<Integer>();
		for (int i = 0; i < 10; i++) {
			test.add(i);
		}
		
		int current = 0;
		while (current < test.size()) {
			System.out.println(test.get(current));
			current++;
		}
		
	}
}